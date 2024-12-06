package com.nevidimka655.astracrypt.features.profile.ui

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nevidimka655.astracrypt.features.profile.Avatars
import com.nevidimka655.astracrypt.features.profile.Avatars.Companion.painter
import com.nevidimka655.astracrypt.model.CoilTinkModel

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
        painter = defaultAvatar.painter(),
        contentDescription = null
    ) else if (coilAvatarModel != null) AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = ImageRequest.Builder(context).apply { data(coilAvatarModel) }.build(),
        contentDescription = null,
        imageLoader = imageLoader,
        contentScale = ContentScale.Crop
    )
}