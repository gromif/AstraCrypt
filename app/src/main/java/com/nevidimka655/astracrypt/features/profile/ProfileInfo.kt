package com.nevidimka655.astracrypt.features.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfo(

    @SerialName("a")
    var defaultAvatar: Int? = null,

    @SerialName("b")
    var name: String? = null

)