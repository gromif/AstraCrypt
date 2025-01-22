package com.nevidimka655.domain.notes.usecase

import com.nevidimka655.domain.notes.repository.Repository

class DeleteByIdUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Long) {
        repository.deleteById(id = id)
    }

}