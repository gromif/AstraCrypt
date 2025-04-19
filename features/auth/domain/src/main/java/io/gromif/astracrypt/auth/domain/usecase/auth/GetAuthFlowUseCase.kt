package io.gromif.astracrypt.auth.domain.usecase.auth

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAuthFlowUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<Auth> {
        return settingsRepository.authFlow
    }

}