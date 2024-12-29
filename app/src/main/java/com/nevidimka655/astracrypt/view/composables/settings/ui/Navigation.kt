package com.nevidimka655.astracrypt.view.composables.settings.ui

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val SettingsUi_UiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_interface)
    )
)

fun NavGraphBuilder.settingsUi(
    onUiStateChange: (UiState) -> Unit,
    navigateToFilesUiSettings: () -> Unit
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