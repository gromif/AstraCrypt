package io.gromif.astracrypt.device_admin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AdminSettingsScreen() {
    val vm: SettingsAdminViewModel = hiltViewModel()

    var adminRightsState by remember { mutableStateOf(vm.isActive) }
    val requestDeviceAdmin = rememberLauncherForActivityResult(vm.requestDeviceAdminContract) {
        adminRightsState = it
    }
    AdminScreen(
        adminRightsGranted = adminRightsState,
        requestAdminRights = { requestDeviceAdmin.launch(null) },
        disableAdminRights = {
            vm.disable()
            adminRightsState = false
        }
    )
}