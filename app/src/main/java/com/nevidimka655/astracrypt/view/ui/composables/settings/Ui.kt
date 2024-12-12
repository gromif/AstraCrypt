package com.nevidimka655.astracrypt.view.ui.composables.settings

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.settings.ui.SettingsUiScreen
import com.nevidimka655.astracrypt.view.ui.tabs.settings.ui.SettingsUiViewModel
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val SettingsUi_UiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_interface)
    )
)

inline fun NavGraphBuilder.settingsUi(
    crossinline onUiStateChange: (UiState) -> Unit,
    noinline navigateToFilesUiSettings: () -> Unit
) = composable<Route.SettingsUi> {
    onUiStateChange(SettingsUi_UiState)
    val vm: SettingsUiViewModel = hiltViewModel()

    val dynamicThemeState by vm.dynamicThemeFlow
        .collectAsStateWithLifecycle(initialValue = true)
    SettingsUiScreen(
        dynamicThemeState = dynamicThemeState,
        isDynamicColorsSupported = vm.isDynamicColorsSupported,
        onDynamicColorsStateChange = { vm.setDynamicColorsState(enabled = it) },
        navigateToFilesUiSettings = navigateToFilesUiSettings
    )
}