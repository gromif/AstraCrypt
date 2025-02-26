package io.gromif.astracrypt.files.data.repository

import android.net.Uri
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.data.util.ExportUtil
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
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

class RepositoryImpl(
    private val filesDao: FilesDao,
    private val aeadHandler: AeadHandler,
    private val fileHandler: FileHandler,
    private val exportUtil: ExportUtil,
    private val itemMapper: Mapper<FilesEntity, Item>,
    private val itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
    private val uriMapper: Mapper<String, Uri>
) : Repository {

    override suspend fun get(aeadInfo: AeadInfo, id: Long): Item {
        var filesEntity = filesDao.get(id).let {
            val databaseMode = aeadInfo.databaseMode
            if (databaseMode is AeadMode.Template) {
                aeadHandler.decryptFilesEntity(aeadInfo, databaseMode, it)
            } else it
        }
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
        ).let {
            val databaseMode = aeadInfo.databaseMode
            if (databaseMode is AeadMode.Template) {
                aeadHandler.encryptFilesEntity(aeadInfo, databaseMode, it)
            } else it
        }
        filesDao.insert(filesEntity)
    }

    override suspend fun delete(aeadInfo: AeadInfo, id: Long) {
        val databaseMode = aeadInfo.databaseMode
        val deque = ArrayDeque<Long>().also { it.add(id) }
        while (deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val (id, file, preview) = filesDao.getDeleteData(currentId).let {
                if (databaseMode is AeadMode.Template) {
                    aeadHandler.decryptDeleteTuple(aeadInfo, databaseMode, it)
                } else it
            }
            filesDao.delete(id)
            if (file != null) with(fileHandler) {
                getFilePath(relativePath = file).delete()
                if (preview != null) getFilePath(relativePath = preview).delete()
            } else {
                val innerIdList = filesDao.getIdList(parent = id)
                deque.addAll(innerIdList)
            }
        }
    }

    override suspend fun move(ids: List<Long>, parent: Long) = filesDao.move(ids, parent)

    override suspend fun rename(
        aeadInfo: AeadInfo,
        id: Long,
        name: String
    ) {
        val databaseMode = aeadInfo.databaseMode

        val name = if (databaseMode is AeadMode.Template) {
            aeadHandler.encryptNameIfNeeded(aeadInfo, databaseMode, name)
        } else name
        filesDao.rename(id, name)
    }

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
        return filesDao.getRecentFilesFlow().map { list ->
            val databaseMode = aeadInfo.databaseMode
            if (databaseMode is AeadMode.Template) list.map {
                val decryptedItem = aeadHandler.decryptFilesEntity(aeadInfo, databaseMode, it)
                itemMapper(decryptedItem)
            } else list.map { itemMapper(it) }
        }
    }

    override suspend fun getItemDetails(aeadInfo: AeadInfo, id: Long): ItemDetails {
        val databaseMode = aeadInfo.databaseMode

        val dto = filesDao.getDetailsById(id).let {
            when (databaseMode) {
                AeadMode.None -> it
                is AeadMode.Template -> aeadHandler.decryptDetailsTuple(aeadInfo, databaseMode, it)
            }
        }
        val itemDetails: ItemDetails
        when (dto.type) {
            ItemType.Folder -> {
                var folderCount = 0
                var filesCount = 0

                val deque = ArrayDeque<Long>()
                deque.add(id)
                while (deque.isNotEmpty()) {
                    val id = deque.removeFirst()
                    val files = filesDao.getIdList(parent = id, excludeFolders = true)
                    filesCount += files.size

                    val folders = filesDao.getIdList(parent = id, typeFilter = dto.type)
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
        currentAeadInfo: AeadInfo,
        targetAeadInfo: AeadInfo
    ) = coroutineScope {
        val currentAeadMode = currentAeadInfo.databaseMode
        val targetAeadMode = targetAeadInfo.databaseMode

        val pageSize = 10
        var offset = 0
        var page: List<UpdateAeadTuple> = listOf()

        suspend fun nextItemsPage(): Boolean {
            page = filesDao.getUpdateAeadTupleList(pageSize, offset)
            offset += page.size
            return page.isNotEmpty()
        }

        while (nextItemsPage()) page.forEach {
            launch {
                var updateTuple = it

                if (currentAeadMode is AeadMode.Template) aeadHandler.decryptUpdateAeadTuple(
                    info = currentAeadInfo,
                    mode = currentAeadMode,
                    data = updateTuple
                ).also { updateTuple = it }

                if (targetAeadMode is AeadMode.Template) aeadHandler.encryptUpdateAeadTuple(
                    info = targetAeadInfo,
                    mode = targetAeadMode,
                    data = updateTuple
                ).also { updateTuple = it }

                filesDao.updateAead(updateTuple)
            }
        }
    }

}