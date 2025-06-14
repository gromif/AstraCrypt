package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAeadPreferenceFlowUseCase(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(): Flow<AeadMode> {
        return settingsRepository.getAeadModeFlow()
    }

}