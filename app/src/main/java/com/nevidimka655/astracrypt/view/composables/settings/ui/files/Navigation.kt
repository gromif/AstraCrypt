package com.nevidimka655.astracrypt.view.composables.settings.ui.files

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val SettingsUiFilesUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files)
    )
)

fun NavGraphBuilder.settingsUiFiles(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsUiFiles> {
    onUiStateChange(SettingsUiFilesUiState)
    val vm: SettingsUiFilesViewModel = hiltViewModel()

    SettingUiFilesScreen(
        filesViewModeFlow = vm.filesViewModeFlow,
        onChangeViewMode = { vm.setFilesViewMode(viewMode = it) }
    )
}