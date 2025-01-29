package io.gromif.astracrypt.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class AvatarDto {

    @SerialName("a1")
    @Serializable
    data object External: AvatarDto()

    @SerialName("a2")
    @Serializable
    data class Default(
        @SerialName("b1")
        val avatar: DefaultAvatarDto = DefaultAvatarDto.AVATAR_5
    ): AvatarDto()

}