package com.nevidimka655.astracrypt.domain.model.profile

import com.nevidimka655.astracrypt.domain.model.profile.Avatars
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfo(

    @SerialName("a")
    var defaultAvatar: Avatars? = null,

    @SerialName("b")
    var name: String? = null

)