package io.gromif.astracrypt.profile.domain.model

sealed class Avatar {

    data object External: Avatar()

    data class Default(
        val defaultAvatar: DefaultAvatar = DefaultAvatar.AVATAR_5
    ): Avatar()

}