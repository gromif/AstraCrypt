package com.nevidimka655.astracrypt.view.navigation.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
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