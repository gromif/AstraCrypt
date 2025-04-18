package io.gromif.secure_content.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesSwitch

@Preview
@Composable
internal fun SecureContentSettingsScreen(
    isActivated: Boolean = true,
    onSetState: (Boolean) -> Unit = {},
    isForced: Boolean = false,
    onSetForcedState: (Boolean) -> Unit = {},
) = PreferencesGroup(text = stringResource(id = R.string.settings_secureContent_title)) {
    PreferencesSwitch(
        isChecked = isActivated,
        titleText = stringResource(R.string.settings_secureContent_switch_title),
        summaryText = stringResource(R.string.settings_secureContent_switch_summary),
        callback = onSetState
    )
    AnimatedVisibility(visible = isActivated) {
        PreferencesSwitch(
            isChecked = isForced,
            titleText = stringResource(R.string.settings_secureContent_switch_force_title),
            summaryText = stringResource(R.string.settings_secureContent_switch_force_summary),
            callback = onSetForcedState
        )
    }
}