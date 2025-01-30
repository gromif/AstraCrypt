package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository
import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class VerifySkinUseCase(
    private val settingsRepository: SettingsRepository,
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(data: String): Boolean = coroutineScope {
        val currentHash = async {
            tinkRepository.computeSkinHash(data = data)
        }
        val savedHash = async {
            settingsRepository.getSkinHash()
        }
        currentHash.await().contentEquals(savedHash.await())
    }

}