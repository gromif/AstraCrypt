package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository
import com.nevidimka655.astracrypt.auth.domain.service.TinkService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class VerifyAuthUseCase(
    private val settingsRepository: SettingsRepository,
    private val tinkService: TinkService
) {

    suspend operator fun invoke(password: String): Boolean = coroutineScope {
        val currentHash = async {
            tinkService.computeAuthHash(data = password)
        }
        val savedHash = async {
            settingsRepository.getAuthHash()
        }
        currentHash.await().contentEquals(savedHash.await())
    }

}