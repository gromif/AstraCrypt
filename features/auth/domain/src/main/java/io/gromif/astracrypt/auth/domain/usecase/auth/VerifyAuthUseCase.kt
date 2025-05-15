package io.gromif.astracrypt.auth.domain.usecase.auth

import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository

class VerifyAuthUseCase(
    private val settingsRepository: SettingsRepository,
    private val repository: Repository
) {

    suspend operator fun invoke(password: String): Boolean {
        val savedHash = settingsRepository.getAuthHash()
        return repository.verifyAuth(
            savedHash = savedHash,
            secret = password
        )
    }
}
