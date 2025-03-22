package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.ValidationRules
import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.repository.Repository

class SetExternalAvatarUseCase(
    private val setProfileUsecase: SetProfileUseCase,
    private val getProfileUsecase: GetProfileUseCase,
    private val getAvatarAeadUseCase: GetAvatarAeadUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(path: String) {
        val avatarAead = getAvatarAeadUseCase()
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