package io.gromif.astracrypt.auth.domain.usecase.skin

import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository

class VerifySkinUseCase(
    private val settingsRepository: SettingsRepository,
    private val repository: Repository
) {

    suspend operator fun invoke(data: String): Boolean {
        val savedHash = settingsRepository.getSkinHash()
        return repository.verifySkin(
            savedHash = savedHash,
            secret = data
        )
    }

}