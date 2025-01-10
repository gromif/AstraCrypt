package io.gromif.astracrypt.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.ui.compose_core.PreferencesScreen
import io.gromif.astracrypt.settings.aead.components.DatabaseGroup
import io.gromif.astracrypt.settings.aead.components.FilesGroup
import io.gromif.astracrypt.settings.aead.components.SettingsGroup

@Preview(showBackground = true)
@Composable
internal fun Screen(
    aeadTemplatesList: List<String> = listOf(),
    aeadLargeStreamTemplateList: List<String> = listOf(),
    aeadSmallStreamTemplateList: List<String> = listOf(),
    notesAeadName: String? = "Test Notes Aead",
    onNotesAeadChange: (Int) -> Unit = {},
    filesAeadName: String? = "Test Files SAead",
    onFilesAeadChange: (Int) -> Unit = {},
    previewAeadName: String? = "Test Preview SAead",
    onPreviewAeadChange: (Int) -> Unit = {},
    settingsAeadName: String? = "Test Settings Aead",
    onSettingsAeadChange: (Int) -> Unit = {}
) {
    PreferencesScreen {
        FilesGroup(
            aeadLargeStreamTemplateList = aeadLargeStreamTemplateList,
            aeadSmallStreamTemplateList = aeadSmallStreamTemplateList,
            filesAeadName = filesAeadName,
            onFilesAeadChange = onFilesAeadChange,
            previewAeadName = previewAeadName,
            onPreviewAeadChange = onPreviewAeadChange
        )
        DatabaseGroup(
            aeadTemplatesList = aeadTemplatesList,
            notesAeadName = notesAeadName,
            onNotesAeadChange = onNotesAeadChange
        )
        SettingsGroup(
            aeadTemplatesList = aeadTemplatesList,
            settingsAeadName = settingsAeadName,
            onSettingsAeadChange = onSettingsAeadChange
        )
    }
}