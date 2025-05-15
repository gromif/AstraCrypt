package io.gromif.astracrypt.auth.domain.usecase.auth

import io.gromif.astracrypt.auth.domain.model.AeadMode
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAeadModeFlowUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<AeadMode> {
        return settingsRepository.getAeadFlow()
    }
}
