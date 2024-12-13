package com.nevidimka655.astracrypt.view.composables.settings.security

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen

@Preview
@Composable
fun SettingsSecurityScreen(
    isActionsSupported: Boolean = true,
    navigateToEncryption: () -> Unit = {},
    navigateToAuth: () -> Unit = {},
    navigateToDeviceAdmin: () -> Unit = {},
    navigateToQuickActions: () -> Unit = {}
) = PreferencesScreen {
    PreferencesGroup {
        Preference(
            titleText = stringResource(id = R.string.settings_encryption),
            callback = navigateToEncryption
        )
        Preference(
            titleText = stringResource(id = R.string.settings_authentication),
            callback = navigateToAuth
        )
        Preference(
            titleText = stringResource(id = R.string.settings_deviceAdminRights),
            callback = navigateToDeviceAdmin
        )
        if (isActionsSupported) Preference(
            titleText = stringResource(id = R.string.settings_quickActions),
            callback = navigateToQuickActions
        )
    }
}