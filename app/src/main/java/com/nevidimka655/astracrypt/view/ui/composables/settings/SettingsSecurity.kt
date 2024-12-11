package com.nevidimka655.astracrypt.view.ui.composables.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.settings.security.SettingsSecurityScreen

inline fun NavGraphBuilder.settingsSecurity(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurity> {
    onUiStateChange(Route.SettingsSecurity.Ui.state)
    SettingsSecurityScreen()
}