package io.gromif.astracrypt.presentation.navigation.settings.security

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

private val SettingsSecurityUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_security)
    )
)

fun NavGraphBuilder.settingsSecurity(
    onUiStateChange: (UiState) -> Unit,
    isActionsSupported: Boolean,
    navigateToAead: () -> Unit,
    navigateToAuth: () -> Unit,
    navigateToDeviceAdmin: () -> Unit,
    navigateToQuickActions: () -> Unit
) = composable<Route.SettingsSecurity> {
    UiStateHandler { onUiStateChange(SettingsSecurityUiState) }
    SettingsSecurityScreen(
        isActionsSupported = isActionsSupported,
        navigateToEncryption = navigateToAead,
        navigateToAuth = navigateToAuth,
        navigateToDeviceAdmin = navigateToDeviceAdmin,
        navigateToQuickActions = navigateToQuickActions
    )
}