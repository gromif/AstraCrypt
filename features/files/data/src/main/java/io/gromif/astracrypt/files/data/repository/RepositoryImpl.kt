package io.gromif.astracrypt.files.data.repository

import android.net.Uri
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.util.AeadHandler
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
            if (aeadInfo.db) aeadHandler.decryptFilesEntity(aeadInfo, it) else it
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
            fileAead = aeadInfo.fileAeadIndex,
            preview = preview,
            previewAead = aeadInfo.previewAeadIndex,
            flags = flags,
            time = time,
            size = size
        ).let {
            if (aeadInfo.db) aeadHandler.encryptFilesEntity(aeadInfo, it) else it
        }
        filesDao.insert(filesEntity)
    }

    override suspend fun delete(aeadInfo: AeadInfo, id: Long) {
        val deque = ArrayDeque<Long>().also { it.add(id) }
        while (deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val (id, file, preview) = filesDao.getDeleteData(currentId).let {
                if (aeadInfo.db) aeadHandler.decryptDeleteTuple(aeadInfo, it) else it
            }
            filesDao.delete(id)
            if (file != null) with(fileHandler) {
                getFilePath(relativePath = file).delete()
                if (preview != null) getFilePath(relativePath = preview).delete()
            } else {
                val innerIdList = filesDao.getIdList(parent = id, typeFilter = ItemType.Folder)
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
        val name = if (aeadInfo.db) aeadHandler.encryptNameIfNeeded(aeadInfo, name) else name
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

    override fun getRecentFilesList(): Flow<List<Item>> {
        return filesDao.getRecentFilesFlow().map { list ->
            list.map { itemMapper(it) }
        }
    }

    override suspend fun getItemDetails(aeadInfo: AeadInfo, id: Long): ItemDetails {
        var dto = filesDao.getDetailsById(id).let {
            if (aeadInfo.db) aeadHandler.decryptDetailsTuple(aeadInfo, it) else it
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

}