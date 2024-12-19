package com.nevidimka655.astracrypt.domain.repository.notes

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.domain.database.NotesPagerTuple
import com.nevidimka655.astracrypt.domain.database.TransformNotesTuple
import com.nevidimka655.astracrypt.data.database.entities.NoteItemEntity

interface NotesRepository {

    suspend fun deleteById(id: Long)

    suspend fun update(noteItemEntity: NoteItemEntity)

    suspend fun insert(noteItemEntity: NoteItemEntity)

    // suspend fun getById(itemId: Long): NoteItemEntity

    suspend fun updateTransform(transformNotesTuple: TransformNotesTuple)

    suspend fun getTransformItems(pageSize: Int, pageIndex: Int): List<TransformNotesTuple>

    suspend fun getTextId(itemId: Long): String?

    fun listOrderDescAsc(): PagingSource<Int, NotesPagerTuple>

}