package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.AeadMode
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class SetAeadModeUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(aeadMode: AeadMode) {
        settingsRepository.setAead(aeadMode = aeadMode)
    }

}