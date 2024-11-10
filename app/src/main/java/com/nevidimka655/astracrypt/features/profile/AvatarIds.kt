package com.nevidimka655.astracrypt.features.profile

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.icons.Avatar1
import com.nevidimka655.astracrypt.ui.theme.icons.Avatar2
import com.nevidimka655.astracrypt.ui.theme.icons.Avatar3
import com.nevidimka655.astracrypt.ui.theme.icons.Avatar4
import com.nevidimka655.astracrypt.ui.theme.icons.Avatar5
import com.nevidimka655.astracrypt.ui.theme.icons.Avatar6
import com.nevidimka655.astracrypt.ui.theme.icons.Avatars
import kotlinx.serialization.Serializable

@Serializable
enum class AvatarIds(val resId: Int) {
    AVATAR_1(R.drawable.avatar_1),
    AVATAR_2(R.drawable.avatar_2),
    AVATAR_3(R.drawable.avatar_3),
    AVATAR_4(R.drawable.avatar_4),
    AVATAR_5(R.drawable.avatar_5),
    AVATAR_6(R.drawable.avatar_6);

    companion object {

        @Composable
        fun AvatarIds.vector() = when (this) {
            AVATAR_1 -> Icons.Avatars.Avatar1
            AVATAR_2 -> Icons.Avatars.Avatar2
            AVATAR_3 -> Icons.Avatars.Avatar3
            AVATAR_4 -> Icons.Avatars.Avatar4
            AVATAR_5 -> Icons.Avatars.Avatar5
            AVATAR_6 -> Icons.Avatars.Avatar6
        }

    }

}