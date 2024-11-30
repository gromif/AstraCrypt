package com.nevidimka655.astracrypt.tabs.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.DynamicColors
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.ui.theme.reset
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch

class InterfaceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            /*AstraCryptTheme {
                val isDynamicColorsAvailable = remember { DynamicColors.isDynamicColorAvailable() }
                PreferencesScreen {
                    PreferencesGroup {
                        Preference(titleText = stringResource(id = R.string.files)) {
                            findNavController().navigate(R.id.action_interfaceFragment_to_filesFragmentAppearance)
                        }
                        if (isDynamicColorsAvailable) {
                            val dynamicColorsState =
                                remember { mutableStateOf(AppearanceManager.useDynamicColors) }
                            PreferencesSwitch(
                                titleText = stringResource(id = R.string.settings_enableDynamicColors),
                                state = dynamicColorsState
                            ) {
                                AppearanceManager.setUseDynamicColors(it)
                                Icons.reset()
                                requireActivity().recreate()
                            }
                        }
                    }
                }
            }*/
        }
    }
}