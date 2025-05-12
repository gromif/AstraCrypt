package io.gromif.notes.data.repository

import io.gromif.astracrypt.utils.Mapper
import io.gromif.notes.data.db.NoteItemEntity
import io.gromif.notes.data.db.NotesDao
import io.gromif.notes.data.db.tuples.TransformNotesTuple
import io.gromif.notes.data.util.AeadHandler
import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.repository.Repository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val dao: NotesDao,
    private val aeadHandler: AeadHandler,
    private val noteMapper: Mapper<NoteItemEntity, Note>
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

    override suspend fun update(
        aead: AeadMode,
        id: Long,
        name: String,
        text: String,
    ) {
        val noteItemEntity = createNoteItemEntity(id = id, name, text).let {
            if (aead is AeadMode.Template) aeadHandler.encryptNoteEntity(aead, it) else it
        }
        dao.update(noteItemEntity = noteItemEntity)
    }

    override suspend fun insert(aead: AeadMode, name: String, text: String) {
        val noteItemEntity = createNoteItemEntity(name = name, text = text).let {
            if (aead is AeadMode.Template) aeadHandler.encryptNoteEntity(aead, it) else it
        }
        dao.insert(noteItemEntity = noteItemEntity)
    }

    override suspend fun getById(aead: AeadMode, id: Long): Note {
        val noteItemEntity = dao.getById(id = id).let {
            if (aead is AeadMode.Template) aeadHandler.decryptNoteEntity(aead, it) else it
        }
        return noteMapper(item = noteItemEntity)
    }

    override suspend fun changeAead(
        sourceAeadMode: AeadMode,
        targetAeadMode: AeadMode,
    ) = coroutineScope {
        val pageSize = 10
        var offset = 0
        var page: List<TransformNotesTuple> = listOf()

        suspend fun nextItemsPage(): Boolean {
            page = dao.getTransformItems(pageSize, offset)
            offset += page.size
            return page.isNotEmpty()
        }

        while (nextItemsPage()) page.forEach {
            val tuple = if (sourceAeadMode is AeadMode.Template) {
                aeadHandler.decryptTransformTuple(aeadTemplate = sourceAeadMode, data = it)
            } else it
            if (targetAeadMode is AeadMode.Template) launch {
                val transformedTuple = aeadHandler.encryptTransformTuple(
                    aeadIndex = targetAeadMode,
                    data = tuple
                )
                dao.updateTransform(transformedTuple)
            } else launch { dao.updateTransform(tuple) }
        }
    }

}