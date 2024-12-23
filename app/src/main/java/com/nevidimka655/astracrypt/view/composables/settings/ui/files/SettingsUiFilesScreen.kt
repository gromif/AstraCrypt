package com.nevidimka655.astracrypt.view.composables.settings.ui.files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.models.ViewMode
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.radio
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingUiFilesScreen(
    filesViewModeFlow: Flow<ViewMode>,
    onChangeViewMode: (ViewMode) -> Unit
) = PreferencesScreen {
    PreferencesGroup {
        val viewMode by filesViewModeFlow.collectAsStateWithLifecycle(initialValue = ViewMode.Grid)
        var dialogViewModeState by DialogsCore.Selectable.radio(
            onSelected = {
                onChangeViewMode(ViewMode.entries[it])
            },
            title = stringResource(id = R.string.viewMode),
            items = ViewMode.entries.map { stringResource(id = it.stringResId) },
            selectedItemIndex = viewMode.ordinal
        )
        Preference(
            titleText = stringResource(id = R.string.viewMode),
            summaryText = stringResource(viewMode.stringResId)
        ) {
            dialogViewModeState = true
        }
    }
}