package io.gromif.astracrypt.files.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.resources.R
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.radio

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