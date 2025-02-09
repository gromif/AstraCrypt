package com.nevidimka655.astracrypt.view.composables.settings.ui.files

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState

private val SettingsUiFilesUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files)
    )
)

fun NavGraphBuilder.settingsUiFiles(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsUiFiles> {
    onUiStateChange(SettingsUiFilesUiState)
    //val vm: SettingsUiFilesViewModel = hiltViewModel()

    /*SettingUiFilesScreen(
        filesViewModeFlow = vm.filesViewModeFlow,
        onChangeViewMode = { vm.setFilesViewMode(viewMode = it) }
    )*/
}