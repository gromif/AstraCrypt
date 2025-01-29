package io.gromif.astracrypt.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    @SerialName("a1")
    val name: String?,

    @SerialName("a2")
    val avatar: AvatarDto,
)