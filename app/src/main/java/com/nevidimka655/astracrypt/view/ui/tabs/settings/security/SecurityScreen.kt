package com.nevidimka655.astracrypt.view.ui.tabs.settings.security

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.utils.AppComponentManager
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen

@Composable
fun SettingsSecurityScreen() = PreferencesScreen {
    PreferencesGroup {
        Preference(titleText = stringResource(id = R.string.settings_encryption)) {
            //findNavController().navigate(R.id.action_securityFragment_to_encryptionFragment)
        }
        Preference(titleText = stringResource(id = R.string.settings_authentication)) {
            //findNavController().navigate(R.id.action_securityFragment_to_authenticationFragment)
        }
        Preference(titleText = stringResource(id = R.string.settings_deviceAdminRights)) {
            //findNavController().navigate(R.id.action_securityFragment_to_deviceAdminRightsFragment)
        }
        val isQuickActionsSupported = remember { AppComponentManager.isActionsSupported }
        if (isQuickActionsSupported) Preference(
            titleText = stringResource(id = R.string.settings_quickActions)
        ) {
            //findNavController().navigate(R.id.action_securityFragment_to_quickActionsFragment)
        }
    }
}