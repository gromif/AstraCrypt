package com.nevidimka655.astracrypt.domain.usecase.notes

import com.nevidimka655.astracrypt.data.database.entities.NoteItemEntity
import com.nevidimka655.astracrypt.domain.repository.notes.NotesRepository

class LoadNoteByIdUseCase(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id: Long): NoteItemEntity {
        return repository.getById(id = id)
    }

}