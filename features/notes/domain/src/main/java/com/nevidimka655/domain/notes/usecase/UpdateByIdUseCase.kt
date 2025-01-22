package com.nevidimka655.domain.notes.usecase

import com.nevidimka655.domain.notes.repository.Repository

class UpdateByIdUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Long, name: String, text: String) {
        repository.update(id = id, name = name, text = text)
    }

}