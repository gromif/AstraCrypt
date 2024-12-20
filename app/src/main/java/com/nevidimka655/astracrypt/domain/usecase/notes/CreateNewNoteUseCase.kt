package com.nevidimka655.astracrypt.domain.usecase.notes

import com.nevidimka655.astracrypt.domain.repository.notes.NotesRepository

class CreateNewNoteUseCase(
    private val repository: NotesRepository
) {

    suspend fun save(name: String, text: String) {
        repository.insert(
            name = name,
            text = text
        )
    }

}