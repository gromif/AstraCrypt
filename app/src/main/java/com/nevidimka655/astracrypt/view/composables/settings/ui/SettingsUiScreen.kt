package com.nevidimka655.astracrypt.view.composables.settings.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.PreferencesSwitch

@Composable
fun SettingsUiScreen(
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