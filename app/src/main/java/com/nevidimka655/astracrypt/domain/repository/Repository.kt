package com.nevidimka655.astracrypt.domain.repository

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.data.files.db.tuples.OpenTuple
import com.nevidimka655.astracrypt.data.files.db.tuples.PagerTuple
import com.nevidimka655.astracrypt.data.files.db.tuples.FilesDirMinimalTuple
import com.nevidimka655.astracrypt.data.files.db.tuples.FilesMinimalTuple
import com.nevidimka655.astracrypt.data.database.FileTypes
import com.nevidimka655.astracrypt.data.files.db.FilesEntity
import com.nevidimka655.astracrypt.data.files.db.tuples.DatabaseTransformTuple
import com.nevidimka655.astracrypt.data.files.db.tuples.ExportTuple
import com.nevidimka655.astracrypt.data.model.DetailsFolderContent
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun insert(item: FilesEntity)

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

    suspend fun getById(itemId: Long): FilesEntity

    suspend fun getMaxId(): Long
    suspend fun getTypeById(id: Long): FileTypes
    suspend fun getDirIdsList(dirId: Long): List<Long>
    suspend fun getFilesCountFlow(dirId: Long): Int
    suspend fun getListDataToExportFromDir(dirId: Long): List<ExportTuple>

    suspend fun getDataToExport(itemId: Long): ExportTuple

    suspend fun getMinimalItemsDataInDir(dirId: Long): List<FilesMinimalTuple>

    suspend fun getMinimalItemData(id: Long): FilesMinimalTuple

    suspend fun getDataForOpening(id: Long): OpenTuple

    suspend fun getParentDirInfo(dirId: Long): FilesDirMinimalTuple?

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