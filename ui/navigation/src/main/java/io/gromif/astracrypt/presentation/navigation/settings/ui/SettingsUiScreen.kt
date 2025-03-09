package io.gromif.astracrypt.presentation.navigation.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen
import io.gromif.ui.compose.core.PreferencesSwitch

@Composable
internal fun SettingsUiScreen(
    dynamicThemeState: Boolean,
    isDynamicColorsSupported: Boolean,
    onDynamicColorsStateChange: (Boolean) -> Unit,
    navigateToFilesUiSettings: () -> Unit
) = PreferencesScreen {
    PreferencesGroup {
        Preference(
            titleText = stringResource(id = R.string.files),
            callback = navigateToFilesUiSettings
        )
        if (isDynamicColorsSupported) PreferencesSwitch(
            isChecked = dynamicThemeState,
            titleText = stringResource(id = R.string.settings_enableDynamicColors),
            callback = onDynamicColorsStateChange
        )
    }
}