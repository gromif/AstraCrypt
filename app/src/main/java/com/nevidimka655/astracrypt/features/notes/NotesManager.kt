package com.nevidimka655.astracrypt.features.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.nevidimka655.astracrypt.data.repository.files.FilesFilesRepositoryImpl
import com.nevidimka655.astracrypt.domain.room.entities.NoteItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesManager(
    private val repository: FilesFilesRepositoryImpl
) {
    var titleState by mutableStateOf(TextFieldValue(""))
    var textState by mutableStateOf(TextFieldValue(""))

    var noteId = 0L

    suspend fun preloadText(
        noteId: Long,
        title: String?
    ) = withContext(Dispatchers.IO) {
        this@NotesManager.noteId = noteId
        /*val text = repository.getNoteTextById(noteId)
        titleState = TextFieldValue(title ?: "")
        textState = TextFieldValue(text ?: "")*/
    }

    suspend fun save() = withContext(Dispatchers.IO) {
        val trimmedTitle = titleState.text.trim()
        val trimmedText = textState.text.trim()
        val textPreview = if (trimmedText.isNotEmpty()) trimmedText.take(80) else null
        if (trimmedTitle.isEmpty() && trimmedText.isEmpty()) return@withContext
        val noteItemEntity = NoteItemEntity(
            id = noteId,
            name = trimmedTitle.ifEmpty { null },
            text = trimmedText,
            textPreview = textPreview,
            creationTime = System.currentTimeMillis()
        )
        /*repository.run {
            if (noteId == 0L) insertNote(noteItemEntity)
            else updateNote(noteItemEntity)
        }*/
        reset()
    }

    suspend fun delete(id: Long) = withContext(Dispatchers.IO) { /*repository.deleteNoteById(id)*/ }

    fun reset() {
        noteId = 0L
        titleState = TextFieldValue("")
        textState = TextFieldValue("")
    }

}