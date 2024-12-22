package com.nevidimka655.domain.notes.usecase

import com.nevidimka655.domain.notes.repository.Repository

class CreateUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(name: String, text: String) {
        repository.insert(
            name = name,
            text = text
        )
    }

}