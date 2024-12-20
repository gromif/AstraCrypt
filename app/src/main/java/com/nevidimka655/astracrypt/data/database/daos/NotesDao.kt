package com.nevidimka655.astracrypt.data.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Update
import com.nevidimka655.astracrypt.data.database.NotesPagerTuple
import com.nevidimka655.astracrypt.data.database.TransformNotesTuple
import com.nevidimka655.astracrypt.data.database.entities.NoteItemEntity

@Dao
interface NotesDao {

    @Query("delete from notes where id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(noteItemEntity: NoteItemEntity)

    @Insert
    suspend fun insert(noteItemEntity: NoteItemEntity)

    @Query("select * from notes WHERE id like :id")
    suspend fun getById(id: Long): NoteItemEntity

    @Update(entity = NoteItemEntity::class)
    suspend fun updateTransform(transformTuple: TransformNotesTuple)

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from notes limit :pageSize offset :pageIndex * :pageSize")
    suspend fun getTransformItems(pageSize: Int, pageIndex: Int): List<TransformNotesTuple>

    @Query("select text from notes WHERE id like :itemId")
    suspend fun getTextId(itemId: Long): String?

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from notes order by id desc")
    fun listOrderDescAsc(): PagingSource<Int, NotesPagerTuple>

}