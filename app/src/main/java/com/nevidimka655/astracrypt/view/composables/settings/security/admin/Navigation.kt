package com.nevidimka655.astracrypt.view.composables.settings.security.admin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

private val SettingsSecurityAdminUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_deviceAdminRights)
    )
)

fun NavGraphBuilder.settingsSecurityAdmin(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.SettingsSecurityAdmin> {
    UiStateHandler { onUiStateChange(SettingsSecurityAdminUiState) }
    val vm: SettingsAdminViewModel = hiltViewModel()

    var adminRightsState by remember { mutableStateOf(vm.isActive) }
    val requestDeviceAdmin = rememberLauncherForActivityResult(vm.requestDeviceAdminContract) {
        adminRightsState = it
    }
    SettingsAdminScreen(
        adminRightsGranted = adminRightsState,
        requestAdminRights = { requestDeviceAdmin.launch(null) },
        disableAdminRights = {
            vm.disable()
            adminRightsState = false
        }
    )
}