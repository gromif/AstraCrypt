package com.nevidimka655.astracrypt.view.composables.settings.security

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val SettingsSecurityUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_security)
    )
)

fun NavGraphBuilder.settingsSecurity(
    onUiStateChange: (UiState) -> Unit,
    navigateToAead: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToDeviceAdmin: () -> Unit,
    navigateToQuickActions: () -> Unit
) = composable<Route.SettingsSecurity> {
    onUiStateChange(SettingsSecurityUiState)
    SettingsSecurityScreen(
        isActionsSupported = Api.atLeast7(),
        navigateToEncryption = navigateToAead,
        navigateToAuth = navigateToAuth,
        navigateToDeviceAdmin = navigateToDeviceAdmin,
        navigateToQuickActions = navigateToQuickActions
    )
}