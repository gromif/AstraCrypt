package io.gromif.astracrypt.files.data.repository.item

import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.item.ItemReader
import io.gromif.astracrypt.utils.Mapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultItemReader(
    private val daoManager: DaoManager,
    private val itemMapper: Mapper<FilesEntity, Item>,
    private val itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
) : ItemReader {
    override suspend fun get(
        aeadInfo: AeadInfo,
        id: Long
    ): Item {
        val filesEntity = daoManager.files(aeadInfo).get(id)
        return itemMapper(filesEntity)
    }

    override suspend fun getFolderIds(
        parentId: Long,
        recursively: Boolean
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

    override suspend fun getRecentFilesList(aeadInfo: AeadInfo): Flow<List<Item>> {
        return daoManager.files(aeadInfo).getRecentFilesFlow().map { list ->
            list.map { itemMapper(it) }
        }
    }

    override suspend fun getItemDetails(
        aeadInfo: AeadInfo,
        id: Long
    ): ItemDetails {
        val filesDaoAead = daoManager.files(aeadInfo)

        val dto = filesDaoAead.getDetailsById(id)
        val itemDetails: ItemDetails
        if (dto.type == ItemType.Folder) {
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
        } else {
            itemDetails = itemDetailsMapper(dto)
        }
        return itemDetails
    }
}
