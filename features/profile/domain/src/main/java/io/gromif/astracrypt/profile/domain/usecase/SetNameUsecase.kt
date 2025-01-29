package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class SetNameUsecase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(name: String) {
        val newProfile = settingsRepository.getProfile().copy(name = name)
        settingsRepository.setProfile(newProfile)
    }

}