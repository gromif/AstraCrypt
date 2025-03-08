package io.gromif.astracrypt.presentation.navigation.settings.security

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_security)
    )
)

internal fun NavGraphBuilder.settingsSecurity(
    isActionsSupported: Boolean,
) = composable<Route.SettingsSecurity> {
    val navController = LocalNavController.current
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    SettingsSecurityScreen(
        isActionsSupported = isActionsSupported,
        navigateToEncryption = { navController.navigate(Route.SettingsSecurityAead) },
        navigateToAuth = { navController.navigate(Route.SettingsSecurityAuth) },
        navigateToDeviceAdmin = { navController.navigate(Route.SettingsSecurityAdmin) },
        navigateToQuickActions = { navController.navigate(Route.SettingsSecurityQuickActions) }
    )
}