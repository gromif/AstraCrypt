package io.gromif.secure_content.domain.usecase

import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.SettingsRepository
import javax.inject.Inject

class SetContentModeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(mode: SecureContentMode) {
        settingsRepository.setSecureContentMode(mode)
    }

}
