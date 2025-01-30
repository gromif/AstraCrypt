package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.repository.Repository
import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class VerifyAuthUseCase(
    private val repository: Repository,
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(password: String): Boolean = coroutineScope {
        val currentHash = async {
            tinkRepository.computeAuthHash(data = password)
        }
        val savedHash = async {
            repository.getAuthHash()
        }
        currentHash.await().contentEquals(savedHash.await())
    }

}