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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.nevidimka655.astracrypt.features.profile.AvatarIds
import com.nevidimka655.astracrypt.features.profile.AvatarIds.Companion.vector
import com.nevidimka655.astracrypt.features.profile.ProfileInfo

@Composable
fun ProfileIcon(
    profileInfo: ProfileInfo,
    imageLoader: ImageLoader,
    showBorder: Boolean = true,
    iconSize: Dp = 80.dp
) {
    val icon = profileInfo.iconFile
    Box(
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
        if (icon == null) {
            if (profileInfo.defaultAvatar != null) Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberVectorPainter(
                    image = AvatarIds.entries[profileInfo.defaultAvatar!!].vector()
                ),
                contentDescription = null
            )
        } else AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = icon,
            contentDescription = null,
            imageLoader = imageLoader,
            contentScale = ContentScale.Crop
        )
    }
}