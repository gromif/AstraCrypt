package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.SettingsRepository

class SetAeadInfoUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(aeadInfo: AeadInfo) {
        return settingsRepository.setAeadInfo(aeadInfo)
    }
}
