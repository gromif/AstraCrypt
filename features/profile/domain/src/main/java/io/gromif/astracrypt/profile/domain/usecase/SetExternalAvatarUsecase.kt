package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.ValidationRules
import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class SetExternalAvatarUsecase(
    private val setProfileUsecase: SetProfileUseCase,
    private val getProfileUsecase: GetProfileUsecase,
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(path: String) {
        val avatarAead = settingsRepository.getAvatarAead()
        repository.importAvatar(
            aead = avatarAead,
            path = path,
            quality = ValidationRules.QUALITY_ICON,
            size = ValidationRules.SIZE_ICON
        )
        val newProfile = getProfileUsecase().copy(avatar = Avatar.External)
        setProfileUsecase(profile = newProfile)
    }

}