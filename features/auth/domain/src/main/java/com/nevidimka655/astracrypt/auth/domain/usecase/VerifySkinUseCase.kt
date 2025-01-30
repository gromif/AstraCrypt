package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.repository.Repository
import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class VerifySkinUseCase(
    private val repository: Repository,
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(data: String): Boolean = coroutineScope {
        val currentHash = async {
            tinkRepository.computeSkinHash(data = data)
        }
        val savedHash = async {
            repository.getSkinHash()
        }
        currentHash.await().contentEquals(savedHash.await())
    }

}