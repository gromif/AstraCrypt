package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class SetProfileUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(profile: Profile) {
        return settingsRepository.setProfile(profile)
    }

}