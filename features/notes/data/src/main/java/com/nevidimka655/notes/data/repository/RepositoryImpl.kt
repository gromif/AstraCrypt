package com.nevidimka655.notes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nevidimka655.astracrypt.notes.db.NoteItemEntity
import com.nevidimka655.astracrypt.notes.db.NotesDao
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.notes.data.mappers.DataToDomainMapper
import com.nevidimka655.domain.notes.model.Note
import com.nevidimka655.domain.notes.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val dao: NotesDao,
    private val dataToDomainMapper: Mapper<NoteItemEntity, Note>
) : Repository {
    override suspend fun deleteById(id: Long) {
        dao.deleteById(id = id)
    }

    private fun createNoteItemEntity(id: Long = 0L, name: String, text: String): NoteItemEntity {
        val trimmedName = name.trim().ifEmpty { null }
        val trimmedText = text.trim()
        val textPreview = if (trimmedText.isNotEmpty()) trimmedText.take(80) else null
        return NoteItemEntity(
            id = id,
            name = trimmedName,
            text = trimmedText,
            textPreview = textPreview,
            creationTime = System.currentTimeMillis()
        )
    }

    override suspend fun update(id: Long, name: String, text: String) {
        val noteItemEntity = createNoteItemEntity(id = id, name, text)
        dao.update(noteItemEntity = noteItemEntity)
    }

    override suspend fun insert(name: String, text: String) {
        val noteItemEntity = createNoteItemEntity(name = name, text = text)
        dao.insert(noteItemEntity = noteItemEntity)
    }

    override suspend fun getById(id: Long): Note {
        val noteItemEntity = dao.getById(id = id)
        return dataToDomainMapper(item = noteItemEntity)
    }

    override suspend fun getByPage(
        pageSize: Int, pageIndex: Int
    ): List<Note> {
        //return dao.getTransformItems(pageSize, pageIndex)
        TODO("DatabaseTransform")
    }

    override fun listOrderDescAsc(): Flow<PagingData<Note>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                dao.listOrderDescAsc()
            }
        ).flow.map { pagingData ->
            pagingData.map { dataToDomainMapper(item = it) }
        }
    }
}