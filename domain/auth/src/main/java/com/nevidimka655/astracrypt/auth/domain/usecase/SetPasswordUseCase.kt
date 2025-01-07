package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.AuthType
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.TinkRepository

class SetPasswordUseCase(
    private val repository: Repository,
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(auth: Auth, data: String?) {
        val authHash = data?.let { tinkRepository.computeAuthHash(data = it) }
        repository.setAuthHash(hash = authHash)
        repository.setAuth(
            auth = auth.copy(
                type = authHash?.let { AuthType.PASSWORD }
            )
        )
    }

}