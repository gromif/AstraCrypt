package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class SetAvatarUsecase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(defaultAvatar: DefaultAvatar) {
        val newProfile = settingsRepository.getProfile().copy(
            avatar = Avatar.Default(defaultAvatar)
        )
        settingsRepository.setProfile(profile = newProfile)
    }

}