package com.nevidimka655.astracrypt.view.composables.settings.security.quick_actions

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState

private val SettingsSecurityQuickActionsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_quickActions)
    )
)

fun NavGraphBuilder.settingsSecurityQuickActions(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurityQuickActions> {
    onUiStateChange(SettingsSecurityQuickActionsUiState)
    val vm: SettingsQuickActionsViewModel = hiltViewModel()
    /*var quickDataDeletion by remember { mutableStateOf(vm.quickDataDeletion) }

    SettingsQuickActionsScreen(
        quickDataDeletion = quickDataDeletion,
        onSetQuickDataDeletion = {
            vm.quickDataDeletion = it
            quickDataDeletion = it
        }
    )*/
}