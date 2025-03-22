package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class GetProfileUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(): Profile {
        return settingsRepository.getProfile()
    }

}