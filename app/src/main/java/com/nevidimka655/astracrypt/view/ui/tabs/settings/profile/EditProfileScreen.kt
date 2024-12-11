package com.nevidimka655.astracrypt.view.ui.tabs.settings.profile

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.profile.Avatars
import com.nevidimka655.astracrypt.features.profile.Avatars.Companion.painter
import com.nevidimka655.astracrypt.features.profile.ui.ProfileIcon
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.app.config.AppConfig
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.dialogs.Dialog
import com.nevidimka655.ui.compose_core.dialogs.DialogDefaults
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.default
import com.nevidimka655.ui.compose_core.theme.spaces

@Composable
fun EditProfileScreen(
    imageLoader: ImageLoader,
    coilAvatarModel: CoilTinkModel?,
    defaultAvatar: Avatars? = null,
    name: String,
    isImageProcessing: Boolean,
    onChangeName: (String) -> Unit,
    onDefaultAvatar: (Avatars) -> Unit,
    onGalleryAvatar: () -> Unit
) = PreferencesScreen {
    val dialogChangeNameState = dialogChangeName(currentName = name) { onChangeName(it.trim()) }
    val dialogChangeAvatarState = Compose.state()
    PreferencesGroup {
        Preference(
            titleText = stringResource(id = R.string.settings_changeName),
            summaryText = name
        ) { dialogChangeNameState.value = true }
        Preference(
            titleText = stringResource(id = R.string.settings_changeAvatar),
            trailingContent = {
                if (!isImageProcessing) ProfileIcon(
                    imageLoader = imageLoader,
                    coilAvatarModel = coilAvatarModel,
                    defaultAvatar = defaultAvatar,
                    iconSize = 56.dp,
                    showBorder = false
                )
            }
        ) { dialogChangeAvatarState.value = true }
    }
    DialogChangeAvatar(
        state = dialogChangeAvatarState,
        galleryCallback = onGalleryAvatar,
        onDefaultAvatarClick = onDefaultAvatar
    )
}

@Composable
private fun dialogChangeName(
    currentName: String,
    onNameChange: (String) -> Unit
) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.settings_changeName),
    params = DialogsCore.TextFields.Params(
        text = currentName,
        label = stringResource(id = R.string.name),
        singleLine = true,
        maxLength = AppConfig.PROFILE_NAME_MAX_SIZE,
    ),
    onResult = onNameChange
)

@Composable
private fun DialogChangeAvatar(
    state: MutableState<Boolean>,
    galleryCallback: () -> Unit,
    onDefaultAvatarClick: (Avatars) -> Unit
) {
    if (!state.value) return
    Dialog(
        title = DialogDefaults.titleCentered(title = stringResource(id = R.string.settings_changeAvatar)),
        onDismissRequest = { state.value = false },
        confirmButton = DialogDefaults.textButton(title = stringResource(id = android.R.string.cancel)) {
            state.value = false
        },
        dismissButton = DialogDefaults.textButton(title = stringResource(id = R.string.gallery)) {
            state.value = false
            galleryCallback()
        },
        content = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
            ) {
                items(Avatars.entries) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .clickable {
                                    state.value = false
                                    onDefaultAvatarClick(it)
                                },
                            painter = it.painter(),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}