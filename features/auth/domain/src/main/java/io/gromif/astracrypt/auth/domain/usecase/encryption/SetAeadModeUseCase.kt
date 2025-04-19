package io.gromif.astracrypt.auth.domain.usecase.encryption

import io.gromif.astracrypt.auth.domain.model.AeadMode
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository

class SetAeadModeUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(aeadMode: AeadMode) {
        settingsRepository.setAead(aeadMode = aeadMode)
    }

}