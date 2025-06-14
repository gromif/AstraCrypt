package io.gromif.astracrypt.presentation.navigation.settings.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostStateHolder
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.wrappers.TextWrap

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_interface)
    )
)

internal fun NavGraphBuilder.settingsUi(
    isDynamicColorsSupported: Boolean,
    onDynamicColorsStateChange: (Boolean) -> Unit,
) = composable<Route.SettingsUi> {
    val navController = LocalNavController.current
    val hostStateHolder = LocalHostStateHolder.current
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    SettingsUiScreen(
        dynamicThemeState = hostStateHolder.dynamicThemeState,
        isDynamicColorsSupported = isDynamicColorsSupported,
        onDynamicColorsStateChange = onDynamicColorsStateChange,
        navigateToFilesUiSettings = {
            navController.navigate(Route.SettingsUiFiles)
        }
    )
}
