package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAeadInfoFlowUseCase(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(): Flow<AeadInfo> {
        return settingsRepository.getAeadInfoFlow()
    }
}
