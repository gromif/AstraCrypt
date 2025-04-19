package io.gromif.astracrypt.auth.domain.usecase.skin

import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class VerifySkinUseCase(
    private val settingsRepository: SettingsRepository,
    private val tinkService: TinkService
) {

    suspend operator fun invoke(data: String): Boolean = coroutineScope {
        val currentHash = async {
            tinkService.computeSkinHash(data = data)
        }
        val savedHash = async {
            settingsRepository.getSkinHash()
        }
        currentHash.await().contentEquals(savedHash.await())
    }

}