package com.nevidimka655.astracrypt.view.ui.composables.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.settings.security.SettingsSecurityScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val SettingsSecurityUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_security)
    )
)

inline fun NavGraphBuilder.settingsSecurity(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurity> {
    onUiStateChange(SettingsSecurityUiState)
    SettingsSecurityScreen()
}