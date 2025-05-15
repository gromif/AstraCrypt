package io.gromif.astracrypt.files.domain.usecase.aead

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.SettingsRepository

class GetAeadInfoUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(): AeadInfo {
        return settingsRepository.getAeadInfo()
    }
}
