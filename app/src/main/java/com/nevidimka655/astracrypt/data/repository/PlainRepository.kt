package com.nevidimka655.astracrypt.data.repository

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.data.model.DetailsFolderContent
import com.nevidimka655.astracrypt.domain.repository.Repository
import com.nevidimka655.astracrypt.domain.room.daos.StorageItemDao
import com.nevidimka655.astracrypt.domain.room.DatabaseTransformTuple
import com.nevidimka655.astracrypt.domain.room.ExportTuple
import com.nevidimka655.astracrypt.domain.room.OpenTuple
import com.nevidimka655.astracrypt.domain.room.PagerTuple
import com.nevidimka655.astracrypt.domain.room.StorageDirMinimalTuple
import com.nevidimka655.astracrypt.domain.room.StorageItemMinimalTuple
import com.nevidimka655.astracrypt.domain.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.data.room.DatabaseColumns
import com.nevidimka655.astracrypt.data.room.StorageItemState
import com.nevidimka655.astracrypt.data.room.StorageItemType
import kotlinx.coroutines.flow.Flow

class PlainRepository(
    private val storage: StorageItemDao
): Repository {
    override suspend fun insert(item: StorageItemEntity) {
        storage.insert(item)
    }

    override suspend fun updateDbEntry(
        id: Long,
        name: String,
        thumb: String,
        path: String,
        flags: String
    ) {
        storage.updateDbEntry(
            DatabaseTransformTuple(id, name, thumb, path, flags)
        )
    }

    override suspend fun newDirectory(name: String, parentId: Long?) {
        val item = StorageItemEntity(
            name = name,
            itemType = StorageItemType.Folder,
            parentDirectoryId = parentId ?: 0,
            creationTime = System.currentTimeMillis()
        )
        insert(item)
    }

    override suspend fun deleteByIds(storageItemIds: ArrayList<Long>) {
        storage.deleteByIds(storageItemIds)
    }

    override suspend fun setStarred(
        id: Long?,
        idsArray: List<Long>?,
        state: Boolean
    ) {
        val newState = if (state) StorageItemState.Starred else StorageItemState.Default
        if (id != null) {
            storage.setStarred(
                id = id,
                state = newState
            )
        } else if (idsArray != null) storage.setStarred(
            idsArray = idsArray,
            state = newState
        )
    }

    override suspend fun moveItems(
        idsArray: List<Long>,
        newDirId: Long
    ) {
        storage.moveItems(idsArray = idsArray, newDirId = newDirId)
    }

    override suspend fun updateName(id: Long, name: String) {
        storage.updateName(id, name)
    }

    override suspend fun getById(itemId: Long): StorageItemEntity {
        return storage.getById(itemId)
    }

    override suspend fun getMaxId(): Long {
        return storage.getMaxId()
    }

    override suspend fun getTypeById(id: Long): StorageItemType {
        return storage.getTypeById(id)
    }

    override suspend fun getDirIdsList(dirId: Long): List<Long> {
        return storage.getDirIdsList(dirId)
    }

    override suspend fun getFilesCountFlow(dirId: Long): Int {
        return storage.getFilesCountFlow(dirId)
    }

    override suspend fun getListDataToExportFromDir(dirId: Long): List<ExportTuple> {
        return storage.getListDataToExport(dirId)
    }

    override suspend fun getDataToExport(itemId: Long): ExportTuple {
        return storage.getDataToExport(itemId)
    }

    override suspend fun getMinimalItemsDataInDir(dirId: Long): List<StorageItemMinimalTuple> {
        return storage.getMinimalItemsDataInDir(dirId)
    }

    override suspend fun getMinimalItemData(id: Long): StorageItemMinimalTuple {
        return storage.getMinimalItemData(id)
    }

    override suspend fun getDataForOpening(id: Long): OpenTuple {
        return storage.getDataToOpen(id)
    }

    override suspend fun getParentDirInfo(dirId: Long): StorageDirMinimalTuple? {
        return storage.getParentDirInfo(dirId)
    }

    override suspend fun getAbsolutePath(
        parentId: Long,
        childName: String
    ): String {
        var path = "/$childName"
        suspend fun dirIterator(dirId: Long) {
            val dirObj = getParentDirInfo(dirId)
            if (dirObj != null) {
                path = "/${dirObj.name}$path"
                if (dirObj.parentDirectoryId != 0L) dirIterator(dirObj.parentDirectoryId)
            }
        }
        if (parentId != 0L) dirIterator(parentId)
        return path
    }

    override suspend fun getDatabaseTransformItems(
        pageSize: Int,
        pageIndex: Int
    ): List<DatabaseTransformTuple> {
        return storage.getDatabaseTransformItems(pageSize, pageIndex)
    }

    override suspend fun getFolderContent(id: Long): DetailsFolderContent {
        var foldersCount = 0
        var filesCount = 0
        suspend fun dirIterator(dirId: Long) {
            val dirsList = getDirIdsList(dirId)
            filesCount += getFilesCountFlow(dirId)
            foldersCount += dirsList.size
            dirsList.forEach {
                dirIterator(it)
            }
        }
        dirIterator(id)
        return DetailsFolderContent(foldersCount, filesCount)
    }

    override fun getRecentFilesFlow(): Flow<List<PagerTuple>> {
        return storage.getRecentFilesFlow()
    }

    override fun getList(
        parentDirectoryId: Long,
        searchQuery: String?,
        dirIdsForSearch: List<Long>
    ): PagingSource<Int, PagerTuple> {
        return storage.listOrderDescAsc(
            parentDirId = if (dirIdsForSearch.isEmpty()) parentDirectoryId else -1,
            query = searchQuery,
            dirIdsForSearch = dirIdsForSearch,
            sortingItemType = StorageItemType.Folder.ordinal,
            sortingSecondType = DatabaseColumns.Name.ordinal
        )
    }

    override fun getStarredList(
        searchQuery: String?
    ): PagingSource<Int, PagerTuple> {
        return storage.listOrderDescAsc(
            isStarredOnly = true,
            query = searchQuery,
            sortingItemType = StorageItemType.Folder.ordinal,
            sortingSecondType = DatabaseColumns.Name.ordinal
        )
    }
}