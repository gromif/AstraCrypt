package com.nevidimka655.astracrypt.room.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.nevidimka655.astracrypt.room.*
import com.nevidimka655.astracrypt.room.entities.NoteItemEntity

@Dao
interface NotesDao {

    @Query("delete from notes where id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(noteItemEntity: NoteItemEntity)

    @Insert
    suspend fun insert(noteItemEntity: NoteItemEntity)

    /*@Query("select * from notes WHERE id like :itemId")
    suspend fun getById(itemId: Long): NoteItemEntity*/

    @Update(entity = NoteItemEntity::class)
    suspend fun updateTransform(transformNotesTuple: TransformNotesTuple)

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from notes limit :pageSize offset :pageIndex * :pageSize")
    suspend fun getTransformItems(pageSize: Int, pageIndex: Int): List<TransformNotesTuple>

    @Query("select text from notes WHERE id like :itemId")
    suspend fun getTextId(itemId: Long): String?

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from notes order by id desc")
    fun listOrderDescAsc(): PagingSource<Int, NoteItemListTuple>

}