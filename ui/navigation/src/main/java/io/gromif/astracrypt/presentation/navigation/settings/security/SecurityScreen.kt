package io.gromif.astracrypt.presentation.navigation.settings.security

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.secure_content.presentation.SecureContentSettings
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen

@Preview
@Composable
internal fun SettingsSecurityScreen(
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
        if (isActionsSupported) {
            Preference(
                titleText = stringResource(id = R.string.settings_quickActions),
                callback = navigateToQuickActions
            )
        }
    }
    SecureContentSettings()
}
