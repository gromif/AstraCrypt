package io.gromif.astracrypt.files.settings.aead.files

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.PreferencesGroup

@Preview(showBackground = true)
@Composable
internal fun FilesGroup(
    fileOptions: List<String> = listOf("Test"),
    fileAeadIndex: Int = 0,
    onFileAeadChange: (Int) -> Unit = {},

    previewOptions: List<String> = listOf("Test"),
    previewAeadIndex: Int = 0,
    onPreviewAeadChange: (Int) -> Unit = {},
) = PreferencesGroup(text = stringResource(id = R.string.files)) {
    FileRadioPreference(
        options = fileOptions,
        selectedIndex = fileAeadIndex,
        onSelect = onFileAeadChange
    )

    PreviewRadioPreference(
        options = previewOptions,
        selectedIndex = previewAeadIndex,
        onSelect = onPreviewAeadChange
    )
}