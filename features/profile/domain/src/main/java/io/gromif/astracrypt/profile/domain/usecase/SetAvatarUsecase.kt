package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar

class SetAvatarUsecase(
    private val setProfileUsecase: SetProfileUseCase,
    private val getProfileUsecase: GetProfileUsecase,
) {

    suspend operator fun invoke(defaultAvatar: DefaultAvatar) {
        val newProfile = getProfileUsecase().copy(
            avatar = Avatar.Default(defaultAvatar)
        )
        setProfileUsecase(profile = newProfile)
    }

}