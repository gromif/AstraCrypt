package com.nevidimka655.astracrypt.view.ui.composables.settings.security

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.settings.security.quick_actions.SettingsQuickActionsScreen
import com.nevidimka655.astracrypt.view.ui.tabs.settings.security.quick_actions.SettingsQuickActionsViewModel
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val SettingsSecurityQuickActionsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_quickActions)
    )
)

inline fun NavGraphBuilder.settingsSecurityQuickActions(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurityQuickActions> {
    onUiStateChange(SettingsSecurityQuickActionsUiState)
    val vm: SettingsQuickActionsViewModel = hiltViewModel()
    var quickDataDeletion by remember { mutableStateOf(vm.quickDataDeletion) }

    SettingsQuickActionsScreen(
        quickDataDeletion = quickDataDeletion,
        onSetQuickDataDeletion = {
            vm.quickDataDeletion = it
            quickDataDeletion = it
        }
    )
}