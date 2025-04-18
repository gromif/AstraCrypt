package io.gromif.secure_content.domain.usecase

import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContentModeFlowUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<SecureContentMode> {
        return settingsRepository.getSecureContentModeFlow()
    }

}
