package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.repository.SettingsRepository

class GetAeadPreferenceUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(): AeadMode {
        return settingsRepository.getAeadMode()
    }

}