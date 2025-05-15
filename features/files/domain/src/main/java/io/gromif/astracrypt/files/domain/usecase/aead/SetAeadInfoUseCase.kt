package io.gromif.astracrypt.files.domain.usecase.aead

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.AeadSettingsRepository

class SetAeadInfoUseCase(
    private val aeadSettingsRepository: AeadSettingsRepository,
) {

    suspend operator fun invoke(aeadInfo: AeadInfo) {
        return aeadSettingsRepository.setAeadInfo(aeadInfo)
    }
}
