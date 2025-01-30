package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository

class GetAuthUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(): Auth {
        return settingsRepository.getAuth()
    }

}