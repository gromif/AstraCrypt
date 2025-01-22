package io.gromif.astracrypt.settings.aead.domain.usecase

import io.gromif.astracrypt.settings.aead.domain.repository.SettingsRepository

class SetSettingsAeadUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(aead: Int) {
        return settingsRepository.setAeadTemplateIndex(aead = aead)
    }

}