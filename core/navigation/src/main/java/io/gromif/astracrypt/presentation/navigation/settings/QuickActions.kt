package io.gromif.astracrypt.presentation.navigation.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.quick_actions.SettingsQuickActionsScreen

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_quickActions)
    )
)

fun NavGraphBuilder.quickActionsSettings(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurityQuickActions> {
    UiStateHandler { onUiStateChange(DefaultUiState) }

    SettingsQuickActionsScreen()
}