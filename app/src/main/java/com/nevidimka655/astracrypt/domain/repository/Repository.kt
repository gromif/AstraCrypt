package com.nevidimka655.astracrypt.domain.repository

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.data.model.DetailsFolderContent
import com.nevidimka655.astracrypt.domain.room.DatabaseTransformTuple
import com.nevidimka655.astracrypt.domain.room.ExportTuple
import com.nevidimka655.astracrypt.domain.room.OpenTuple
import com.nevidimka655.astracrypt.domain.room.PagerTuple
import com.nevidimka655.astracrypt.domain.room.StorageDirMinimalTuple
import com.nevidimka655.astracrypt.domain.room.StorageItemMinimalTuple
import com.nevidimka655.astracrypt.domain.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.data.room.StorageItemType
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insert(item: StorageItemEntity)

    suspend fun updateDbEntry(
        id: Long, name: String, thumb: String, path: String, flags: String
    )

    suspend fun newDirectory(name: String, parentId: Long?)

    suspend fun deleteByIds(storageItemIds: ArrayList<Long>)

    suspend fun setStarred(
        id: Long? = null, idsArray: List<Long>? = null, state: Boolean
    )

    suspend fun moveItems(idsArray: List<Long>, newDirId: Long)

    suspend fun updateName(id: Long, name: String)

    suspend fun getById(itemId: Long): StorageItemEntity

    suspend fun getMaxId(): Long
    suspend fun getTypeById(id: Long): StorageItemType
    suspend fun getDirIdsList(dirId: Long): List<Long>
    suspend fun getFilesCountFlow(dirId: Long): Int
    suspend fun getListDataToExportFromDir(dirId: Long): List<ExportTuple>

    suspend fun getDataToExport(itemId: Long): ExportTuple

    suspend fun getMinimalItemsDataInDir(dirId: Long): List<StorageItemMinimalTuple>

    suspend fun getMinimalItemData(id: Long): StorageItemMinimalTuple

    suspend fun getDataForOpening(id: Long): OpenTuple

    suspend fun getParentDirInfo(dirId: Long): StorageDirMinimalTuple?

    suspend fun getAbsolutePath(parentId: Long, childName: String): String

    suspend fun getDatabaseTransformItems(pageSize: Int, pageIndex: Int): List<DatabaseTransformTuple>

    suspend fun getFolderContent(id: Long): DetailsFolderContent

    fun getRecentFilesFlow(): Flow<List<PagerTuple>>

    fun getList(
        parentDirectoryId: Long,
        searchQuery: String? = null,
        dirIdsForSearch: List<Long>
    ): PagingSource<Int, PagerTuple>

    fun getStarredList(searchQuery: String? = null): PagingSource<Int, PagerTuple>

}