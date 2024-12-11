package com.nevidimka655.astracrypt.view.ui.settings.security

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.app.utils.DeviceAdminManager
import com.nevidimka655.astracrypt.app.utils.contracts.RequestAdminRights
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch
import com.nevidimka655.ui.compose_core.banners.Banner
import com.nevidimka655.ui.compose_core.banners.Warning

class DeviceAdminRightsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                val context = LocalContext.current
                val devicePolicyManager = remember { DeviceAdminManager.init(context) }
                val adminRightsState =
                    remember { mutableStateOf(isAdminRightsGranted(context, devicePolicyManager)) }
                val requestAdminRightsContract = rememberLauncherForActivityResult(
                    RequestAdminRights()
                ) {
                    adminRightsState.value = it
                }
                PreferencesScreen {
                    Banner.Warning(text = stringResource(id = R.string.settings_deviceAdminRights_warning))
                    PreferencesGroup {
                        PreferencesSwitch(
                            titleText = stringResource(id = R.string.settings_deviceAdminRights),
                            summaryText = stringResource(id = R.string.settings_deviceAdminRights_summary),
                            state = adminRightsState,
                            isAutoSwitchState = false
                        ) {
                            if (it) requestAdminRightsContract.launch(null)
                            else {
                                adminRightsState.value = false
                                DeviceAdminManager.revokeAdminRights(context, devicePolicyManager)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isAdminRightsGranted(context: Context, devicePolicyManager: DevicePolicyManager) =
        DeviceAdminManager.isAdminRightsGranted(context, devicePolicyManager)
}