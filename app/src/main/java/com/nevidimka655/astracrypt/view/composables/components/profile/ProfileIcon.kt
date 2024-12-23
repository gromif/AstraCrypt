package com.nevidimka655.astracrypt.view.composables.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.domain.model.profile.Avatars

@Composable
fun ProfileIcon(
    imageLoader: ImageLoader,
    coilAvatarModel: CoilTinkModel?,
    defaultAvatar: Avatars? = null,
    showBorder: Boolean = true,
    iconSize: Dp = 80.dp
) = Box(
    modifier = Modifier
        .size(iconSize)
        .run {
            if (showBorder) {
                border(3.dp, MaterialTheme.colorScheme.onSurfaceVariant, CircleShape)
                    .padding(9.dp)
            } else this
        }
        .clip(CircleShape),
    contentAlignment = Alignment.Center
) {
    val context = LocalContext.current
    if (defaultAvatar != null) Image(
        modifier = Modifier.fillMaxSize(),
        painter = defaultProfileAvatar(defaultAvatar),
        contentDescription = null
    ) else if (coilAvatarModel != null) AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = ImageRequest.Builder(context).apply { data(coilAvatarModel) }.build(),
        contentDescription = null,
        imageLoader = imageLoader,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun defaultProfileAvatar(defaultAvatar: Avatars = Avatars.AVATAR_1) = painterResource(
    when (defaultAvatar) {
        Avatars.AVATAR_1 -> R.drawable.avatar_1
        Avatars.AVATAR_2 -> R.drawable.avatar_2
        Avatars.AVATAR_3 -> R.drawable.avatar_3
        Avatars.AVATAR_4 -> R.drawable.avatar_4
        Avatars.AVATAR_5 -> R.drawable.avatar_5
        Avatars.AVATAR_6 -> R.drawable.avatar_6
        Avatars.AVATAR_7 -> R.drawable.avatar_7
        Avatars.AVATAR_8 -> R.drawable.avatar_8
        Avatars.AVATAR_9 -> R.drawable.avatar_9
    }
)