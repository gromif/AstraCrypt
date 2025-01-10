package io.gromif.astracrypt.settings.aead.domain.usecase

import io.gromif.astracrypt.settings.aead.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsAeadUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<String?> {
        return settingsRepository.getAeadNameFlow()
    }

}