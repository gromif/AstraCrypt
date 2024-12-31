package com.nevidimka655.astracrypt.data.repository

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.data.database.DatabaseTransformTuple
import com.nevidimka655.astracrypt.data.database.ExportTuple
import com.nevidimka655.astracrypt.data.database.OpenTuple
import com.nevidimka655.astracrypt.data.database.PagerTuple
import com.nevidimka655.astracrypt.data.database.StorageDirMinimalTuple
import com.nevidimka655.astracrypt.data.database.StorageItemMinimalTuple
import com.nevidimka655.astracrypt.data.database.StorageItemType
import com.nevidimka655.astracrypt.data.database.daos.StorageItemDao
import com.nevidimka655.astracrypt.data.database.entities.StorageItemEntity
import com.nevidimka655.astracrypt.data.model.DetailsFolderContent
import com.nevidimka655.astracrypt.domain.model.db.StorageColumns
import com.nevidimka655.astracrypt.domain.model.db.StorageState
import com.nevidimka655.astracrypt.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(private val dao: StorageItemDao): Repository {
    override suspend fun insert(item: StorageItemEntity) {
        dao.insert(item)
    }

    override suspend fun updateDbEntry(
        id: Long,
        name: String,
        thumb: String,
        path: String,
        flags: String
    ) {
        dao.updateDbEntry(
            DatabaseTransformTuple(id, name, thumb, path, flags)
        )
    }

    override suspend fun newDirectory(name: String, parentId: Long?) {
        val item = StorageItemEntity(
            name = name,
            type = StorageItemType.Folder.ordinal,
            dirId = parentId ?: 0,
            creationTime = System.currentTimeMillis()
        )
        insert(item)
    }

    override suspend fun deleteByIds(storageItemIds: ArrayList<Long>) {
        dao.deleteByIds(storageItemIds)
    }

    override suspend fun setStarred(
        id: Long?,
        idsArray: List<Long>?,
        state: Boolean
    ) {
        val newState = if (state) StorageState.Starred else StorageState.Default
        if (id != null) {
            dao.setStarred(
                id = id,
                state = newState
            )
        } else if (idsArray != null) dao.setStarred(
            idsArray = idsArray,
            state = newState
        )
    }

    override suspend fun moveItems(
        idsArray: List<Long>,
        newDirId: Long
    ) {
        dao.moveItems(idsArray = idsArray, newDirId = newDirId)
    }

    override suspend fun updateName(id: Long, name: String) {
        dao.updateName(id, name)
    }

    override suspend fun getById(itemId: Long): StorageItemEntity {
        return dao.getById(itemId)
    }

    override suspend fun getMaxId(): Long {
        return dao.getMaxId()
    }

    override suspend fun getTypeById(id: Long): StorageItemType {
        return dao.getTypeById(id)
    }

    override suspend fun getDirIdsList(dirId: Long): List<Long> {
        return dao.getDirIdsList(dirId)
    }

    override suspend fun getFilesCountFlow(dirId: Long): Int {
        return dao.getFilesCountFlow(dirId)
    }

    override suspend fun getListDataToExportFromDir(dirId: Long): List<ExportTuple> {
        return dao.getListDataToExport(dirId)
    }

    override suspend fun getDataToExport(itemId: Long): ExportTuple {
        return dao.getDataToExport(itemId)
    }

    override suspend fun getMinimalItemsDataInDir(dirId: Long): List<StorageItemMinimalTuple> {
        return dao.getMinimalItemsDataInDir(dirId)
    }

    override suspend fun getMinimalItemData(id: Long): StorageItemMinimalTuple {
        return dao.getMinimalItemData(id)
    }

    override suspend fun getDataForOpening(id: Long): OpenTuple {
        return dao.getDataToOpen(id)
    }

    override suspend fun getParentDirInfo(dirId: Long): StorageDirMinimalTuple? {
        return dao.getParentDirInfo(dirId)
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
        return dao.getDatabaseTransformItems(pageSize, pageIndex)
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
        return dao.getRecentFilesFlow()
    }

    override fun getList(
        parentDirectoryId: Long,
        searchQuery: String?,
        dirIdsForSearch: List<Long>
    ): PagingSource<Int, PagerTuple> {
        return dao.listOrderDescAsc(
            parentDirId = if (dirIdsForSearch.isEmpty()) parentDirectoryId else -1,
            query = searchQuery,
            dirIdsForSearch = dirIdsForSearch,
            sortingItemType = StorageItemType.Folder.ordinal,
            sortingSecondType = StorageColumns.Name.ordinal
        )
    }

    override fun getStarredList(
        searchQuery: String?
    ): PagingSource<Int, PagerTuple> {
        return dao.listOrderDescAsc(
            isStarredOnly = true,
            query = searchQuery,
            sortingItemType = StorageItemType.Folder.ordinal,
            sortingSecondType = StorageColumns.Name.ordinal
        )
    }
}