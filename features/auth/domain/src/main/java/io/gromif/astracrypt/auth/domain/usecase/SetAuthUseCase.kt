package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository

class SetAuthUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(auth: Auth) {
        settingsRepository.setAuth(auth)
    }

}