package com.nevidimka655.astracrypt.domain.usecase.notes

import com.nevidimka655.astracrypt.domain.repository.notes.NotesRepository

class UpdateNoteByIdUseCase(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id: Long, name: String, text: String) {
        repository.update(id = id, name = name, text = text)
    }

}