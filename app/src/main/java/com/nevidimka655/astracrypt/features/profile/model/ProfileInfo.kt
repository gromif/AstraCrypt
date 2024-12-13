package com.nevidimka655.astracrypt.features.profile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfo(

    @SerialName("a")
    var defaultAvatar: Avatars? = null,

    @SerialName("b")
    var name: String? = null

)