package com.nevidimka655.astracrypt.domain.repository.notes

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.data.database.NotesPagerTuple
import com.nevidimka655.astracrypt.data.database.TransformNotesTuple
import com.nevidimka655.astracrypt.data.database.entities.NoteItemEntity

interface NotesRepository {

    suspend fun deleteById(id: Long)

    suspend fun update(id: Long, name: String, text: String)

    suspend fun insert(name: String, text: String)

    suspend fun getById(id: Long): NoteItemEntity

    suspend fun updateTransform(transformNotesTuple: TransformNotesTuple)

    suspend fun getTransformItems(pageSize: Int, pageIndex: Int): List<TransformNotesTuple>

    fun listOrderDescAsc(): PagingSource<Int, NotesPagerTuple>

}