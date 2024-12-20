package com.nevidimka655.astracrypt.domain.usecase.notes

import com.nevidimka655.astracrypt.domain.repository.notes.NotesRepository

class DeleteByIdUseCase(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(id: Long) {
        repository.deleteById(id = id)
    }

}