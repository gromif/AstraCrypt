package com.nevidimka655.astracrypt.tabs.settings.security.quick_actions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch

class QuickActionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                val quickDataDeletionState =
                    remember { mutableStateOf(isQuickDataDeletionEnabled()) }
                PreferencesScreen {
                    PreferencesGroup {
                        PreferencesSwitch(
                            titleText = stringResource(id = R.string.settings_quickActions_dataDeletion),
                            state = quickDataDeletionState,
                            isAutoSwitchState = false
                        ) {
                            QuickActionsManager.setComponentState(
                                componentName = QuickActionsManager.Components.QUICK_DATA_DELETION,
                                state = it
                            )
                            quickDataDeletionState.value = isQuickDataDeletionEnabled()
                        }
                    }
                }
            }
        }
    }

    private fun isQuickDataDeletionEnabled() =
        QuickActionsManager.isComponentEnabled(QuickActionsManager.Components.QUICK_DATA_DELETION)

}