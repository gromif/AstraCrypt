package io.gromif.astracrypt.profile.presentation.shared

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
import com.nevidimka655.ui.compose_core.ext.effects.shimmerEffect
import io.gromif.astracrypt.profile.domain.model.Avatar

@Composable
internal fun Avatar(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    avatar: Avatar?,
    showBorder: Boolean = true,
    iconSize: Dp = 80.dp,
) = Box(
    modifier = modifier
        .size(iconSize)
        .run {
            if (showBorder) {
                border(3.dp, MaterialTheme.colorScheme.onSurfaceVariant, CircleShape)
                    .padding(9.dp)
            } else this
        }
        .clip(CircleShape)
        .run {
            if (avatar == null) shimmerEffect() else this
        },
    contentAlignment = Alignment.Center
) {
    if (avatar == null) return@Box
    val context = LocalContext.current
    when (avatar) {
        is Avatar.External -> AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context).data(avatar).build(),
            contentDescription = null,
            imageLoader = imageLoader,
            contentScale = ContentScale.Crop
        )
        is Avatar.Default -> Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(avatar.defaultAvatar.resource()),
            contentDescription = null
        )
    }
}