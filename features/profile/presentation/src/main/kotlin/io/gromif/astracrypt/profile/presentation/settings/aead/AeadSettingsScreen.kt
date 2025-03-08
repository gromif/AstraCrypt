package io.gromif.astracrypt.profile.presentation.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.preferences.RadioPreference

@Preview(showBackground = true)
@Composable
internal fun AeadSettingsScreen(
    params: AeadSettingsScreenParams = AeadSettingsScreenParams(),
    actions: AeadSettingsScreenActions = AeadSettingsScreenActions.DEFAULT,
) = PreferencesGroup(
    text = stringResource(id = R.string.settings_profile)
) {
    RadioPreference(
        title = stringResource(R.string.settings),
        items = params.settingsOptions,
        selectedIndex = params.settingsOptionIndex,
        onSelected = actions::onSettingsChanged
    )
}