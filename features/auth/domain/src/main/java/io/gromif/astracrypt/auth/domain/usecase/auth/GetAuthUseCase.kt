package io.gromif.astracrypt.auth.domain.usecase.auth

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository

class GetAuthUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(): Auth {
        return settingsRepository.getAuth()
    }

}