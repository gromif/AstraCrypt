package io.gromif.astracrypt.device_admin.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AdminSettings() {
    val vm: AdminSettingsViewModel = hiltViewModel()
    val state by vm.adminState.collectAsStateWithLifecycle()

    val requestDeviceAdmin = rememberLauncherForActivityResult(vm.requestDeviceAdminContract) {}

    val adminState = state
    if (adminState != null) AdminSettingsScreen(
        adminRightsGranted = adminState.isActive,
        requestAdminRights = { requestDeviceAdmin.launch(null) },
        disableAdminRights = vm::disable
    )
}