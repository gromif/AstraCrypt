package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.Repository

class VerifyPasswordUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(password: String): Boolean {
        return repository.verifyPassword(password = password)
    }

}