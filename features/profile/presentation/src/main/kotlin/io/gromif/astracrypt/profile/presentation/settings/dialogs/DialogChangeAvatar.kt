package io.gromif.astracrypt.profile.presentation.settings.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.presentation.shared.resource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.dialogs.Dialog
import io.gromif.ui.compose.core.dialogs.DialogDefaults
import io.gromif.ui.compose.core.theme.spaces

@Preview(showBackground = true)
@Composable
internal fun DialogChangeAvatar(
    state: Boolean = true,
    galleryCallback: () -> Unit = {},
    onDefaultAvatarClick: (DefaultAvatar) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    if (!state) return
    Dialog(
        title = DialogDefaults.titleCentered(title = stringResource(id = R.string.settings_changeAvatar)),
        onDismissRequest = onDismissRequest,
        confirmButton = DialogDefaults.textButton(title = stringResource(id = android.R.string.cancel)) {
            onDismissRequest()
        },
        dismissButton = DialogDefaults.textButton(title = stringResource(id = R.string.gallery)) {
            onDismissRequest()
            galleryCallback()
        },
        content = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
            ) {
                items(DefaultAvatar.entries) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .clickable {
                                    onDismissRequest()
                                    onDefaultAvatarClick(it)
                                },
                            painter = painterResource(id = it.resource()),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}