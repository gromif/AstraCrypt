package io.gromif.astracrypt.presentation.navigation.settings.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

private val SettingsUi_UiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_interface)
    )
)

fun NavGraphBuilder.settingsUi(
    onUiStateChange: (UiState) -> Unit,
    navigateToFilesUiSettings: () -> Unit,
    dynamicThemeState: Boolean,
    isDynamicColorsSupported: Boolean,
    onDynamicColorsStateChange: (Boolean) -> Unit,
) = composable<Route.SettingsUi> {
    UiStateHandler { onUiStateChange(SettingsUi_UiState) }

    SettingsUiScreen(
        dynamicThemeState = dynamicThemeState,
        isDynamicColorsSupported = isDynamicColorsSupported,
        onDynamicColorsStateChange = onDynamicColorsStateChange,
        navigateToFilesUiSettings = navigateToFilesUiSettings
    )
}