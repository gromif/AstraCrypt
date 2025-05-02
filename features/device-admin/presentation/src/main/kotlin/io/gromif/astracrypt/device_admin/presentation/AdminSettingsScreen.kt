package io.gromif.astracrypt.device_admin.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen
import io.gromif.ui.compose.core.PreferencesSwitch
import io.gromif.ui.compose.core.banners.Banner
import io.gromif.ui.compose.core.banners.Warning

@Composable
internal fun AdminSettingsScreen(
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