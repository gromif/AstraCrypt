package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.repository.SettingsRepository

class SetAeadPreferenceUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(targetAead: AeadMode) {
        settingsRepository.setAeadMode(targetAead)
    }

}