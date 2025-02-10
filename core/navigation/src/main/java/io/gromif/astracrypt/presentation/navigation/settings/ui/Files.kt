package io.gromif.astracrypt.presentation.navigation.settings.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.settings.UiSettingsScreen
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

private val SettingsUiFilesUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files)
    )
)

fun NavGraphBuilder.filesUiSettings(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsUiFiles> {
    UiStateHandler { onUiStateChange(SettingsUiFilesUiState) }
    UiSettingsScreen()
}