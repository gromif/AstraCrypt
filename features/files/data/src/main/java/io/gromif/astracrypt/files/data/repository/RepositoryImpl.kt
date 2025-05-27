package io.gromif.astracrypt.files.data.repository

import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
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
    private val daoManager: DaoManager,
    private val fileHandler: FileHandler,
    private val itemMapper: Mapper<FilesEntity, Item>,
    private val itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
) : Repository {

    override suspend fun get(aeadInfo: AeadInfo, id: Long): Item {
        val filesEntity = daoManager.files(aeadInfo).get(id)
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
            val innerFolderIds = daoManager.files().getIdList(parent = id, typeFilter = ItemType.Folder)
            idList.addAll(innerFolderIds)
            if (recursively) deque.addAll(innerFolderIds)
        }
        idList.toList()
    }

    override suspend fun insert(
        aeadInfo: AeadInfo,
        importItemDto: ImportItemDto
    ) {
        val filesEntity = FilesEntity(
            parent = importItemDto.parent,
            name = importItemDto.name,
            state = importItemDto.itemState,
            type = importItemDto.itemType,
            file = importItemDto.file,
            fileAead = aeadInfo.fileMode.id,
            preview = importItemDto.preview,
            previewAead = aeadInfo.previewMode.id,
            flags = importItemDto.flags,
            time = importItemDto.creationTime,
            size = importItemDto.size
        )
        daoManager.files(aeadInfo).insert(filesEntity)
    }

    override suspend fun delete(aeadInfo: AeadInfo, id: Long) {
        val filesDaoAead = daoManager.files(aeadInfo)
        val deque = ArrayDeque<Long>().also { it.add(id) }
        while (deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val (id, file, preview) = filesDaoAead.getDeleteData(currentId)
            filesDaoAead.delete(id)
            if (file != null) {
                with(fileHandler) {
                    getFilePath(relativePath = file).delete()
                    if (preview != null) getFilePath(relativePath = preview).delete()
                }
            } else {
                val innerIdList = filesDaoAead.getIdList(parent = id)
                deque.addAll(innerIdList)
            }
        }
    }

    override suspend fun move(ids: List<Long>, parentId: Long) {
        daoManager.files().move(ids, parentId)
    }

    override suspend fun rename(
        aeadInfo: AeadInfo,
        id: Long,
        name: String
    ) = daoManager.files(aeadInfo).rename(id, name)

    override suspend fun setState(id: Long, state: ItemState) {
        daoManager.files().setStarred(id = id, state = state.ordinal)
    }

    override suspend fun getRecentFilesList(aeadInfo: AeadInfo): Flow<List<Item>> {
        return daoManager.files(aeadInfo).getRecentFilesFlow().map { list ->
            list.map { itemMapper(it) }
        }
    }

    override suspend fun getItemDetails(aeadInfo: AeadInfo, id: Long): ItemDetails {
        val filesDaoAead = daoManager.files(aeadInfo)

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

}
