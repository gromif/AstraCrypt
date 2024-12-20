package com.nevidimka655.astracrypt.data.repository.notes

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.data.database.NotesPagerTuple
import com.nevidimka655.astracrypt.data.database.TransformNotesTuple
import com.nevidimka655.astracrypt.data.database.daos.NotesDao
import com.nevidimka655.astracrypt.data.database.entities.NoteItemEntity
import com.nevidimka655.astracrypt.domain.repository.notes.NotesRepository

class NotesRepositoryImpl(private val dao: NotesDao) : NotesRepository {
    override suspend fun deleteById(id: Long) {
        dao.deleteById(id = id)
    }

    override suspend fun update(noteItemEntity: NoteItemEntity) {
        dao.update(noteItemEntity = noteItemEntity)
    }

    override suspend fun insert(name: String, text: String) {
        val trimmedName = name.trim().ifEmpty { null }
        val trimmedText = text.trim()
        val textPreview = if (trimmedText.isNotEmpty()) trimmedText.take(80) else null
        val noteItemEntity = NoteItemEntity(
            name = trimmedName,
            text = trimmedText,
            textPreview = textPreview,
            creationTime = System.currentTimeMillis()
        )
        dao.insert(noteItemEntity = noteItemEntity)
    }

    override suspend fun getById(id: Long): NoteItemEntity {
        return dao.getById(id = id)
    }

    override suspend fun updateTransform(transformTuple: TransformNotesTuple) {
        dao.updateTransform(transformTuple = transformTuple)
    }

    override suspend fun getTransformItems(
        pageSize: Int, pageIndex: Int
    ): List<TransformNotesTuple> {
        return dao.getTransformItems(pageSize, pageIndex)
    }

    override suspend fun getTextId(itemId: Long): String? {
        return dao.getTextId(itemId)
    }

    override fun listOrderDescAsc(): PagingSource<Int, NotesPagerTuple> {
        return dao.listOrderDescAsc()
    }
}