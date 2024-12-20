package com.nevidimka655.astracrypt.view.composables.settings.security

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.utils.AppComponentService
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val SettingsSecurityUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_security)
    )
)

inline fun NavGraphBuilder.settingsSecurity(
    crossinline onUiStateChange: (UiState) -> Unit,
    noinline navigateToEncryption: () -> Unit,
    noinline navigateToAuth: () -> Unit,
    noinline navigateToDeviceAdmin: () -> Unit,
    noinline navigateToQuickActions: () -> Unit
) = composable<Route.SettingsSecurity> {
    onUiStateChange(SettingsSecurityUiState)
    SettingsSecurityScreen(
        isActionsSupported = AppComponentService.isActionsSupported,
        navigateToEncryption = navigateToEncryption,
        navigateToAuth = navigateToAuth,
        navigateToDeviceAdmin = navigateToDeviceAdmin,
        navigateToQuickActions = navigateToQuickActions
    )
}