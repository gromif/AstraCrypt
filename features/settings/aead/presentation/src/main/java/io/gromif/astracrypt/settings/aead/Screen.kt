package io.gromif.astracrypt.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.notes.settings.AeadSettings
import com.nevidimka655.ui.compose_core.PreferencesScreen
import io.gromif.astracrypt.auth.presentation.settings.aead.AuthAeadSettingsScreen
import io.gromif.astracrypt.files.settings.aead.FilesAeadSettings
import io.gromif.astracrypt.profile.presentation.settings.aead.ProfileAeadSettingsScreen
import io.gromif.astracrypt.settings.aead.components.SettingsGroup

@Preview(showBackground = true)
@Composable
internal fun Screen(
    aeadTemplatesList: List<String> = listOf(),
    toDatabaseColumnsAeadSettings: () -> Unit = {},
    settingsAeadName: String? = "Test Settings Aead",
    onSettingsAeadChange: (Int) -> Unit = {}
) {
    PreferencesScreen {
        FilesAeadSettings(
            toDatabaseColumnsAeadSettings = toDatabaseColumnsAeadSettings
        )
        AeadSettings()
        AuthAeadSettingsScreen()
        ProfileAeadSettingsScreen()
        SettingsGroup(
            aeadTemplatesList = aeadTemplatesList,
            settingsAeadName = settingsAeadName,
            onSettingsAeadChange = onSettingsAeadChange
        )
    }
}