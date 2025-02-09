package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
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