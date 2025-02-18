package io.gromif.astracrypt.files.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Update
import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.domain.model.ItemType
import kotlinx.coroutines.flow.Flow

@Dao
interface FilesDao {

    @Query("select * from store_items where id = :id")
    suspend fun get(id: Long): FilesEntity

    @Query("select id from store_items " +
            "where parent = :parent " +
            "and (not :excludeFolders or type > 0)" +
            "and (:typeFilter is null or type = :typeFilter)")
    suspend fun getIdList(
        parent: Long,
        typeFilter: ItemType? = null,
        excludeFolders: Boolean = false,
    ): List<Long>

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where id = :id")
    suspend fun getDeleteData(id: Long): DeleteTuple

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where id = :id")
    suspend fun getDetailsById(id: Long): DetailsTuple

    @Insert
    suspend fun insert(filesEntity: FilesEntity)

    @Query("delete from store_items where id = :id")
    suspend fun delete(id: Long)

    @Query("update store_items set parent = (:parent) where id in (:ids)")
    suspend fun move(ids: List<Long>, parent: Long)

    @Query("update store_items set name = :name where id = :id")
    suspend fun rename(id: Long, name: String)

    @Query("update store_items set state = :state where id = :id")
    suspend fun setStarred(id: Long, state: Int)

    @Update(entity = FilesEntity::class)
    suspend fun updateAead(updateAeadTuple: UpdateAeadTuple)

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items where id = :id")
    suspend fun getExportData(id: Long): ExportTuple

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items limit :pageSize offset :pageIndex * :pageSize")
    suspend fun getUpdateAeadTupleList(
        pageSize: Int,
        pageIndex: Int
    ): List<UpdateAeadTuple>

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from store_items order by id desc limit 10")
    fun getRecentFilesFlow(): Flow<List<FilesEntity>>

    // Note glob (instead of like) for case sens (replace % with *)
    @RewriteQueriesToDropUnusedColumns
    @Query(
        "select * from store_items where " +
        "(" +
            "(:query is null and parent = :rootId) " +
            "or " +
            "(:query is not null and parent in (:rootIdsToSearch))" +
        ") " +
        "and (state = 0 or state = 2) " +
        "and (:query is null or name like '%' || :query || '%') " +
        "order by type = :sortingItemType desc, " +
        "case :sortingSecondType " +
        "   when 6 then id " +
        "   when 1 then name " +
        "end"
    )
    fun listDefault(
        rootId: Long = 0,
        query: String? = null,
        rootIdsToSearch: List<Long> = emptyList(),
        sortingItemType: Int,
        sortingSecondType: Int
    ): PagingSource<Int, PagerTuple>

    // Note glob (instead of like) for case sens (replace % with *)
    @RewriteQueriesToDropUnusedColumns
    @Query(
        "select * from store_items where state = 2 " +
        "and (:query is null or name like '%' || :query || '%') " +
        "order by type = :sortingItemType desc, " +
        "case :sortingSecondType " +
        "   when 6 then id " +
        "   when 1 then name " +
        "end"
    )
    fun listStarred(
        query: String? = null,
        sortingItemType: Int,
        sortingSecondType: Int
    ): PagingSource<Int, PagerTuple>

}