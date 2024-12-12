package com.nevidimka655.astracrypt.view.ui.composables.settings.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.settings.ui.files.SettingUiFilesScreen
import com.nevidimka655.astracrypt.view.ui.tabs.settings.ui.files.SettingsUiFilesViewModel
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val SettingsUiFilesUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files)
    )
)

inline fun NavGraphBuilder.settingsUiFiles(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsUiFiles> {
    onUiStateChange(SettingsUiFilesUiState)
    val vm: SettingsUiFilesViewModel = hiltViewModel()

    SettingUiFilesScreen(
        filesViewModeFlow = vm.filesViewModeFlow,
        onChangeViewMode = { vm.setFilesViewMode(viewMode = it) }
    )
}