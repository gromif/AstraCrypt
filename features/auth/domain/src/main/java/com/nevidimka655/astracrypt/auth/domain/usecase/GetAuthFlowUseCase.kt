package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAuthFlowUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<Auth> {
        return settingsRepository.authFlow
    }

}