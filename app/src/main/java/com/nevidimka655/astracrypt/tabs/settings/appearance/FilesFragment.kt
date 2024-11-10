package com.nevidimka655.astracrypt.tabs.settings.appearance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.appearance.AppearanceManager
import com.nevidimka655.astracrypt.utils.appearance.ViewMode
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.dialogs.Dialogs
import com.nevidimka655.ui.compose_core.dialogs.radio

class FilesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                PreferencesScreen {
                    PreferencesGroup {
                        val viewModeDialogItems = remember { listOf(getString(R.string.viewMode_grid), getString(R.string.viewMode_list)) }
                        var selectedViewModeIndex by
                        remember { mutableIntStateOf(AppearanceManager.viewMode.ordinal) }
                        var dialogViewModeState by Dialogs.Selectable.radio(
                            onSelected = {
                                AppearanceManager.setViewMode(ViewMode.entries[it])
                                selectedViewModeIndex = it
                            },
                            title = stringResource(id = R.string.viewMode),
                            items = viewModeDialogItems,
                            selectedItemIndex = AppearanceManager.viewMode.ordinal
                        )
                        Preference(
                            titleText = stringResource(id = R.string.viewMode),
                            summaryText = viewModeDialogItems[selectedViewModeIndex]
                        ) {
                            dialogViewModeState = true
                        }
                    }
                }
            }
        }
    }
}