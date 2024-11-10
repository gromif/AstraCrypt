package com.nevidimka655.astracrypt.features.profile

import android.graphics.drawable.Drawable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ProfileInfo(

    @SerialName("a")
    var defaultAvatar: Int? = null,

    @SerialName("b")
    var name: String? = null,

    @Transient
    var iconFile: Drawable? = null

)