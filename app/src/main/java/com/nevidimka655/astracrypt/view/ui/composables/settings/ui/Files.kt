package com.nevidimka655.astracrypt.view.ui.composables.settings.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.settings.ui.files.SettingUiFilesScreen
import com.nevidimka655.astracrypt.view.ui.tabs.settings.ui.files.SettingsUiFilesViewModel

inline fun NavGraphBuilder.settingsUiFiles(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsUiFiles> {
    onUiStateChange(Route.SettingsUiFiles.Ui.state)
    val vm: SettingsUiFilesViewModel = hiltViewModel()

    SettingUiFilesScreen(
        filesViewModeFlow = vm.filesViewModeFlow,
        onChangeViewMode = { vm.setFilesViewMode(viewMode = it) }
    )
}