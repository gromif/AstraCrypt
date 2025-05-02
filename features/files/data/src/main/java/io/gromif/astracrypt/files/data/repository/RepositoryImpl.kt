package io.gromif.astracrypt.files.data.repository

import android.net.Uri
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesDaoAeadAdapter
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.data.util.ExportUtil
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.utils.Mapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class RepositoryImpl(
    private val filesDao: FilesDao,
    private val filesDaoAeadAdapterFactory: FilesDaoAeadAdapter.Factory,
    private val fileHandler: FileHandler,
    private val exportUtil: ExportUtil,
    private val itemMapper: Mapper<FilesEntity, Item>,
    private val itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
    private val uriMapper: Mapper<String, Uri>
) : Repository {
    private val mutex = Mutex()
    private var cachedFilesDaoAeadAdapter: FilesDaoAeadAdapter? = null

    private suspend fun getFilesDaoAead(aeadInfo: AeadInfo): FilesDao = mutex.withLock {
        val cached = cachedFilesDaoAeadAdapter
        if (cached != null && cached.compareAeadInfo(aeadInfo)) return cached

        return filesDaoAeadAdapterFactory.create(aeadInfo).also { cachedFilesDaoAeadAdapter = it }
    }

    override suspend fun get(aeadInfo: AeadInfo, id: Long): Item {
        val filesEntity = getFilesDaoAead(aeadInfo).get(id)
        return itemMapper(filesEntity)
    }

    override suspend fun getFolderIds(
        parentId: Long,
        recursively: Boolean,
    ): List<Long> = coroutineScope {
        val idList = mutableListOf<Long>().also { it.add(parentId) }
        val deque = ArrayDeque<Long>().also { it.add(parentId) }

        while (deque.isNotEmpty()) {
            ensureActive()
            val id = deque.removeFirst()
            val innerFolderIds = filesDao.getIdList(parent = id, typeFilter = ItemType.Folder)
            idList.addAll(innerFolderIds)
            if (recursively) deque.addAll(innerFolderIds)
        }
        idList.toList()
    }

    override suspend fun insert(
        aeadInfo: AeadInfo,
        parent: Long,
        name: String,
        itemState: ItemState,
        itemType: ItemType,
        file: String?,
        preview: String?,
        flags: String?,
        creationTime: Long,
        size: Long,
    ) {
        val time = if (creationTime == 0L) System.currentTimeMillis() else creationTime
        val filesEntity = FilesEntity(
            parent = parent,
            name = name,
            state = itemState,
            type = itemType,
            file = file,
            fileAead = aeadInfo.fileMode.id,
            preview = preview,
            previewAead = aeadInfo.previewMode.id,
            flags = flags,
            time = time,
            size = size
        )
        getFilesDaoAead(aeadInfo).insert(filesEntity)
    }

    override suspend fun delete(aeadInfo: AeadInfo, id: Long) {
        val filesDaoAead = getFilesDaoAead(aeadInfo)
        val deque = ArrayDeque<Long>().also { it.add(id) }
        while (deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val (id, file, preview) = filesDaoAead.getDeleteData(currentId)
            filesDaoAead.delete(id)
            if (file != null) with(fileHandler) {
                getFilePath(relativePath = file).delete()
                if (preview != null) getFilePath(relativePath = preview).delete()
            } else {
                val innerIdList = filesDaoAead.getIdList(parent = id)
                deque.addAll(innerIdList)
            }
        }
    }

    override suspend fun move(ids: List<Long>, parentId: Long) = filesDao.move(ids, parentId)

    override suspend fun rename(
        aeadInfo: AeadInfo,
        id: Long,
        name: String
    ) = getFilesDaoAead(aeadInfo).rename(id, name)

    override suspend fun setState(id: Long, state: ItemState) {
        filesDao.setStarred(id = id, state = state.ordinal)
    }

    override suspend fun export(idList: List<Long>, outputPath: String) {
        val uri = uriMapper(outputPath)
        if (exportUtil.createDocumentFile(uri)) exportUtil.use {
            it.startExternally(idList = idList)
        }
    }

    override suspend fun exportPrivately(id: Long): String? {
        return exportUtil.use {
            it.startPrivately(id)
        }
    }

    override suspend fun getRecentFilesList(aeadInfo: AeadInfo): Flow<List<Item>> {
        return getFilesDaoAead(aeadInfo).getRecentFilesFlow().map { list ->
            list.map { itemMapper(it) }
        }
    }

    override suspend fun getItemDetails(aeadInfo: AeadInfo, id: Long): ItemDetails {
        val filesDaoAead = getFilesDaoAead(aeadInfo)

        val dto = filesDaoAead.getDetailsById(id)
        val itemDetails: ItemDetails
        when (dto.type) {
            ItemType.Folder -> {
                var folderCount = 0
                var filesCount = 0

                val deque = ArrayDeque<Long>()
                deque.add(id)
                while (deque.isNotEmpty()) {
                    val id = deque.removeFirst()
                    val files = filesDaoAead.getIdList(parent = id, excludeFolders = true)
                    filesCount += files.size

                    val folders = filesDaoAead.getIdList(parent = id, typeFilter = dto.type)
                    folderCount += folders.size

                    deque.addAll(folders)
                }

                itemDetails = ItemDetails.Folder(
                    name = dto.name,
                    filesCount = filesCount,
                    foldersCount = folderCount,
                    creationTime = dto.time
                )
            }
            else -> itemDetails = itemDetailsMapper(dto)
        }
        return itemDetails
    }

    override suspend fun changeAead(
        oldAeadInfo: AeadInfo,
        targetAeadInfo: AeadInfo
    ) = coroutineScope {
        val currentFilesDaoAead = getFilesDaoAead(aeadInfo = oldAeadInfo)
        val targetFilesDaoAead = filesDaoAeadAdapterFactory.create(aeadInfo = targetAeadInfo)

        val pageSize = 10
        var offset = 0
        var page: List<UpdateAeadTuple> = listOf()

        suspend fun nextItemsPage(): Boolean {
            page = currentFilesDaoAead.getUpdateAeadTupleList(pageSize, offset)
            offset += page.size
            return page.isNotEmpty()
        }

        while (nextItemsPage()) page.forEach {
            launch {
                targetFilesDaoAead.updateAead(it)
            }
        }
    }

}