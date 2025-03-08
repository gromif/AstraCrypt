package io.gromif.astracrypt.presentation.navigation.settings.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostStateHolder
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

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