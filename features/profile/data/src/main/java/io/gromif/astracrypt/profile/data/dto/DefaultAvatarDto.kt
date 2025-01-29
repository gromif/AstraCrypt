package io.gromif.astracrypt.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DefaultAvatarDto {
    @SerialName("a1") AVATAR_1,
    @SerialName("a2") AVATAR_2,
    @SerialName("b1") AVATAR_3,
    @SerialName("b2") AVATAR_4,
    @SerialName("c1") AVATAR_5,
    @SerialName("c2") AVATAR_6,
    @SerialName("d1") AVATAR_7,
    @SerialName("d2") AVATAR_8,
    @SerialName("e1") AVATAR_9;
}