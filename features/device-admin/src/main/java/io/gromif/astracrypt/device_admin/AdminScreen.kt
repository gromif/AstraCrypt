package io.gromif.astracrypt.device_admin

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch
import com.nevidimka655.ui.compose_core.banners.Banner
import com.nevidimka655.ui.compose_core.banners.Warning

@Composable
internal fun AdminScreen(
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