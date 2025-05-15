package io.gromif.astracrypt.auth.presentation.settings.preferences

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.PreferencesSwitch

@Composable
internal fun HintStatePreference(
    isEnabled: Boolean,
    onStateChange: (Boolean) -> Unit
) {
    PreferencesSwitch(
        titleText = stringResource(id = R.string.settings_showHint),
        isChecked = isEnabled,
        callback = onStateChange
    )
}
