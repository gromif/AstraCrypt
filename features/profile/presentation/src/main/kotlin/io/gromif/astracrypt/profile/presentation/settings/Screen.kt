package io.gromif.astracrypt.profile.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.presentation.settings.dialogs.DialogChangeAvatar
import io.gromif.astracrypt.profile.presentation.settings.dialogs.dialogChangeName
import io.gromif.astracrypt.profile.presentation.shared.Avatar
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Compose
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen

@Preview(showBackground = true)
@Composable
internal fun Screen(
    maxNameLength: Int = 30,
    profile: Profile = Profile(name = "User"),
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    hideIcon: Boolean = false,
    actions: Actions = Actions.default,
) = PreferencesScreen {
    val (name, avatar) = profile
    val formattedName = name ?: stringResource(R.string.user)
    val dialogChangeNameState = dialogChangeName(
        maxLength = maxNameLength,
        name = formattedName,
        onResult = actions::changeName
    )
    var dialogChangeAvatarState by Compose.state()
    PreferencesGroup {
        Preference(
            titleText = stringResource(id = R.string.settings_changeName),
            summaryText = formattedName
        ) { dialogChangeNameState.value = true }
        Preference(
            titleText = stringResource(id = R.string.settings_changeAvatar),
            trailingContent = {
                if (!hideIcon) Avatar(
                    imageLoader = imageLoader,
                    avatar = avatar,
                    showBorder = false,
                    iconSize = 56.dp
                )
            }
        ) { dialogChangeAvatarState = true }
    }
    DialogChangeAvatar(
        state = dialogChangeAvatarState,
        galleryCallback = actions::setExternalAvatar,
        onDefaultAvatarClick = actions::setDefaultAvatar,
        onDismissRequest = { dialogChangeAvatarState = false }
    )
}
