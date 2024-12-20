package com.nevidimka655.astracrypt.data.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Update
import com.nevidimka655.astracrypt.data.database.DatabaseTransformTuple
import com.nevidimka655.astracrypt.data.database.ExportTuple
import com.nevidimka655.astracrypt.data.database.OpenTuple
import com.nevidimka655.astracrypt.data.database.PagerTuple
import com.nevidimka655.astracrypt.data.database.StorageDirMinimalTuple
import com.nevidimka655.astracrypt.data.database.StorageItemMinimalTuple
import com.nevidimka655.astracrypt.data.database.StorageItemType
import com.nevidimka655.astracrypt.data.database.entities.StorageItemEntity
import com.nevidimka655.astracrypt.domain.model.db.StorageState
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageItemDao {

    @Query("select max(id) from store_items")
    suspend fun getMaxId(): Long

    @Query("select item_type from store_items where id = :id")
    suspend fun getTypeById(id: Long): StorageItemType

    @Query("select * from store_items WHERE id = :id")
    suspend fun getById(id: Long): StorageItemEntity

    @Query("delete from store_items where id in (:ids)")
    suspend fun deleteByIds(ids: ArrayList<Long>)

    @Insert
    suspend fun insert(storageItems: StorageItemEntity)

    @Update(entity = StorageItemEntity::class)
    suspend fun updateDbEntry(databaseTransformTuple: DatabaseTransformTuple)

    @Query("update store_items set name = :name where id = :id")
    suspend fun updateName(id: Long, name: String)

    @Query("update store_items set state = :state where id = :id")
    suspend fun setStarred(id: Long, state: StorageState)

    @Query("update store_items set state = :state where id in (:idsArray)")
    suspend fun setStarred(idsArray: List<Long>, state: StorageState)

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where id = :id")
    suspend fun getMinimalItemData(id: Long): StorageItemMinimalTuple

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where id = :id")
    suspend fun getDataToOpen(id: Long): OpenTuple

    @Query("select name from store_items where id = :id")
    suspend fun getName(id: Long): String

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where id = :dirId")
    suspend fun getParentDirInfo(dirId: Long): StorageDirMinimalTuple?

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where dir_id = :dirId")
    suspend fun getMinimalItemsDataInDir(dirId: Long): List<StorageItemMinimalTuple>

    @Query("update store_items set dir_id = :newDirId where id in (:idsArray)")
    suspend fun moveItems(idsArray: List<Long>, newDirId: Long)

    @Query("select id from store_items where dir_id = :dirId and item_type = 0")
    suspend fun getDirIdsList(dirId: Long): List<Long>

    @Query("select count(*) from store_items where dir_id = :dirId and item_type > 0")
    suspend fun getFilesCountFlow(dirId: Long): Int

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where dir_id = :dirId")
    suspend fun getListDataToExport(dirId: Long): List<ExportTuple>

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where id = :itemId")
    suspend fun getDataToExport(itemId: Long): ExportTuple

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items limit :pageSize offset :pageIndex * :pageSize")
    suspend fun getDatabaseTransformItems(
        pageSize: Int,
        pageIndex: Int
    ): List<DatabaseTransformTuple>

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items order by id desc limit 10")
    fun getRecentFilesFlow(): Flow<List<PagerTuple>>

    @Query("select count(id) from store_items where item_type > 0")
    fun getFilesCountFlow(): Flow<Int>

    @Query("select count(id) from store_items where item_type > 0")
    suspend fun getFilesCount(): Int

    // Note glob (instead of like) for case sens (replace % with *)
    @RewriteQueriesToDropUnusedColumns
    @Query(
        "select * from store_items where " +
                "case " +
                "   when :isStarredOnly = 0 then " +
                "       case " +
                "           when :parentDirId = -1 then dir_id in (:dirIdsForSearch) " +
                "           when (:query is null) then dir_id = :parentDirId " +
                "           else dir_id > -1" +
                "       end " +
                "       and (state = 0 or state = 2) " +
                "       and (:query is null or name like '%' || :query || '%') " +
                "   else state = 2 and (:query is null or name like '%' || :query || '%') " +
                "end " +
                "order by item_type = :sortingItemType desc, " +
                "case " +
                "   when :sortingSecondType = 6 then id " +
                "   when :sortingSecondType = 1 then name " +
                "end"
    )
    fun listOrderDescAsc(
        parentDirId: Long = 0,
        isStarredOnly: Boolean = false,
        query: String? = null,
        dirIdsForSearch: List<Long> = emptyList(),
        sortingItemType: Int,
        sortingSecondType: Int
    ): PagingSource<Int, PagerTuple>

}