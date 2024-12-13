package com.nevidimka655.astracrypt.features.profile.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nevidimka655.astracrypt.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Avatars(val resId: Int) {
    @SerialName("a") AVATAR_1(R.drawable.avatar_1),
    @SerialName("b") AVATAR_2(R.drawable.avatar_2),
    @SerialName("c") AVATAR_3(R.drawable.avatar_3),
    @SerialName("d") AVATAR_4(R.drawable.avatar_4),
    @SerialName("e") AVATAR_5(R.drawable.avatar_5),
    @SerialName("f") AVATAR_6(R.drawable.avatar_6),
    @SerialName("g") AVATAR_7(R.drawable.avatar_7),
    @SerialName("h") AVATAR_8(R.drawable.avatar_8),
    @SerialName("i") AVATAR_9(R.drawable.avatar_9);

    companion object {

        @Composable
        fun Avatars.painter() = painterResource(id = this.resId)

    }

}