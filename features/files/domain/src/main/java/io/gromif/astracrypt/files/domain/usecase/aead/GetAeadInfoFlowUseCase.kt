package io.gromif.astracrypt.files.domain.usecase.aead

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.AeadSettingsRepository
import kotlinx.coroutines.flow.Flow

class GetAeadInfoFlowUseCase(
    private val aeadSettingsRepository: AeadSettingsRepository,
) {

    operator fun invoke(): Flow<AeadInfo> {
        return aeadSettingsRepository.getAeadInfoFlow()
    }
}
