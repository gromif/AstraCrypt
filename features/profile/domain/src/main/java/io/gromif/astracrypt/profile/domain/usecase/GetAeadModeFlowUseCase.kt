package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.AeadMode
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAeadModeFlowUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<AeadMode> {
        return settingsRepository.getAeadFlow()
    }

}