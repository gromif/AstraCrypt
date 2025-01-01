package com.nevidimka655.astracrypt.view.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.auth.settings.AuthSettingsScreen
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val SettingsSecurityAuthUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_authentication)
    )
)

fun NavGraphBuilder.settingsSecurityAuth(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurityAuth> {
    onUiStateChange(SettingsSecurityAuthUiState)
    AuthSettingsScreen()
}