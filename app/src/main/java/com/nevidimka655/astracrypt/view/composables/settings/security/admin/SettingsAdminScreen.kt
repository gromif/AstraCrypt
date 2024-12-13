package com.nevidimka655.astracrypt.view.composables.settings.security.admin

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch
import com.nevidimka655.ui.compose_core.banners.Banner
import com.nevidimka655.ui.compose_core.banners.Warning

@Composable
fun SettingsAdminScreen(
    adminRightsGranted: Boolean = true,
    requestAdminRights: () -> Unit = {},
    disableAdminRights: () -> Unit = {}
) = PreferencesScreen {
    Banner.Warning(text = stringResource(id = R.string.settings_deviceAdminRights_warning))
    PreferencesGroup {
        PreferencesSwitch(
            titleText = stringResource(id = R.string.settings_deviceAdminRights),
            summaryText = stringResource(id = R.string.settings_deviceAdminRights_summary),
            isChecked = adminRightsGranted
        ) {
            if (it) requestAdminRights()
            else disableAdminRights()
        }
    }
}