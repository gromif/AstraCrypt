package io.gromif.astracrypt.quick_actions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch

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