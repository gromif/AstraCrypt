package io.gromif.astracrypt.quick_actions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen
import io.gromif.ui.compose.core.PreferencesSwitch

@Preview
@Composable
internal fun QuickActionsScreen(
    quickDataDeletion: Boolean = true,
    onSetQuickDataDeletion: (Boolean) -> Unit = {},
) = PreferencesScreen {
    PreferencesGroup {
        PreferencesSwitch(
            titleText = stringResource(id = R.string.settings_quickActions_dataDeletion),
            isChecked = quickDataDeletion,
            callback = onSetQuickDataDeletion
        )
    }
}