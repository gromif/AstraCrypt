package io.gromif.astracrypt.profile.presentation.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.ext.effects.shimmerEffect
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.presentation.shared.Avatar

@Preview(showBackground = true)
@Composable
internal fun Widget(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    profile: Profile? = Profile(name = "User"),
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceLarge),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Avatar(
        imageLoader = imageLoader,
        avatar = profile?.avatar,
        iconSize = 120.dp
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spaces.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .run {
                if (profile == null) shimmerEffect() else this
            },
            text = if (profile == null) "" else stringResource(
                id = R.string.hello, profile.name ?: stringResource(R.string.user)
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
