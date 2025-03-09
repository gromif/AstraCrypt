package io.gromif.astracrypt.auth.presentation.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.preferences.RadioPreference

@Preview(showBackground = true)
@Composable
internal fun AeadSettingsScreen(
    params: AeadSettingsScreenParams = AeadSettingsScreenParams(),
    actions: AeadSettingsScreenActions = AeadSettingsScreenActions.DEFAULT,
) = PreferencesGroup(
    text = stringResource(id = R.string.settings_authentication)
) {
    RadioPreference(
        title = stringResource(R.string.settings),
        items = params.settingsOptions,
        selectedIndex = params.settingsOptionIndex,
        onSelected = actions::onSettingsChanged
    )
}