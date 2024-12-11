package com.nevidimka655.astracrypt.features.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.profile.Avatars
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.ui.compose_core.theme.spaces

@Composable
fun ProfileWidget(
    imageLoader: ImageLoader,
    name: String? = null,
    coilAvatarModel: CoilTinkModel?,
    defaultAvatar: Avatars? = null
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
) {
    ProfileIcon(
        imageLoader = imageLoader,
        coilAvatarModel = coilAvatarModel,
        defaultAvatar = defaultAvatar
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spaces.spaceMedium),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = stringResource(
                id = R.string.hello, name ?: stringResource(R.string.user)
            ),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = R.string.welcome_1),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
