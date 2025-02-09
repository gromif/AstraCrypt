package io.gromif.astracrypt.profile.presentation.shared

import androidx.annotation.DrawableRes
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.presentation.R

@DrawableRes
fun DefaultAvatar.resource(): Int = when(this) {
    DefaultAvatar.AVATAR_1 -> R.drawable.avatar_1
    DefaultAvatar.AVATAR_2 -> R.drawable.avatar_2
    DefaultAvatar.AVATAR_3 -> R.drawable.avatar_3
    DefaultAvatar.AVATAR_4 -> R.drawable.avatar_4
    DefaultAvatar.AVATAR_5 -> R.drawable.avatar_5
    DefaultAvatar.AVATAR_6 -> R.drawable.avatar_6
    DefaultAvatar.AVATAR_7 -> R.drawable.avatar_7
    DefaultAvatar.AVATAR_8 -> R.drawable.avatar_8
    DefaultAvatar.AVATAR_9 -> R.drawable.avatar_9
}