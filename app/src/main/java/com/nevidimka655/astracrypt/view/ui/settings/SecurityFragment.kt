package com.nevidimka655.astracrypt.view.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.app.utils.AppComponentManager
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen

class SecurityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                val isQuickActionsSupported = remember { AppComponentManager.isActionsSupported }
                PreferencesScreen {
                    PreferencesGroup {
                        Preference(titleText = stringResource(id = R.string.settings_encryption)) {
                            findNavController().navigate(R.id.action_securityFragment_to_encryptionFragment)
                        }
                        Preference(titleText = stringResource(id = R.string.settings_authentication)) {
                            findNavController().navigate(R.id.action_securityFragment_to_authenticationFragment)
                        }
                        Preference(titleText = stringResource(id = R.string.settings_deviceAdminRights)) {
                            findNavController().navigate(R.id.action_securityFragment_to_deviceAdminRightsFragment)
                        }
                        if (isQuickActionsSupported) Preference(
                            titleText = stringResource(id = R.string.settings_quickActions)
                        ) {
                            findNavController().navigate(R.id.action_securityFragment_to_quickActionsFragment)
                        }
                    }
                }
            }
        }
    }
}