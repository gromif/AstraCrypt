package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar

class SetAvatarUseCase(
    private val setProfileUsecase: SetProfileUseCase,
    private val getProfileUsecase: GetProfileUseCase,
) {

    suspend operator fun invoke(defaultAvatar: DefaultAvatar) {
        val newProfile = getProfileUsecase().copy(
            avatar = Avatar.Default(defaultAvatar)
        )
        setProfileUsecase(profile = newProfile)
    }

}