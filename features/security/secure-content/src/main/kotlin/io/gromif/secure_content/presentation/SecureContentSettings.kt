package io.gromif.secure_content.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.secure_content.domain.SecureContentMode

@Composable
fun SecureContentSettings() {
    val vm: SecureContentSettingsViewModel = hiltViewModel()
    val contentMode by vm.contentModeFlow.collectAsStateWithLifecycle(null)

    if (contentMode != null) SecureContentSettingsScreen(
        isActivated = contentMode != SecureContentMode.DISABLED,
        onSetState = {
            val newMode = if (it) SecureContentMode.ENABLED else SecureContentMode.DISABLED
            vm.setMode(newMode)
        },
        isForced = contentMode == SecureContentMode.FORCE,
        onSetForcedState = {
            val newMode = if (it) SecureContentMode.FORCE else SecureContentMode.ENABLED
            vm.setMode(newMode)
        }
    )
}