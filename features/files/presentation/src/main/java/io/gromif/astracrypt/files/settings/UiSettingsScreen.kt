package io.gromif.astracrypt.files.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.radio
import io.gromif.astracrypt.files.domain.model.ViewMode

@Composable
fun UiSettingsScreen() {
    val vm: UiSettingsViewModel = hiltViewModel()
    val viewMode by vm.viewModeState.collectAsStateWithLifecycle()
    val viewModeItems = listOf(
        stringResource(R.string.viewMode_grid),
        stringResource(R.string.viewMode_list),
    )
    PreferencesScreen {
        PreferencesGroup {
            var dialogViewModeState by DialogsCore.Selectable.radio(
                onSelected = {
                    vm.setViewMode(ViewMode.entries[it])
                },
                title = stringResource(id = R.string.viewMode),
                items = viewModeItems,
                selectedItemIndex = viewMode.ordinal
            )
            Preference(
                titleText = stringResource(id = R.string.viewMode),
                summaryText = viewModeItems[viewMode.ordinal]
            ) {
                dialogViewModeState = true
            }
        }
    }
}