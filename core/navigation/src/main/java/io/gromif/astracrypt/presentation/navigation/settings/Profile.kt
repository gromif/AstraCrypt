package io.gromif.astracrypt.presentation.navigation.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.profile.presentation.settings.SettingsScreen
import io.gromif.astracrypt.profile.presentation.shared.Profile

private val ProfileSettingsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_editProfile)
    )
)


fun NavGraphBuilder.profileSettings(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.EditProfile> {
    UiStateHandler { onUiStateChange(ProfileSettingsUiState) }

    Profile.SettingsScreen()
}