package io.gromif.astracrypt.settings.aead.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.PreferencesGroup
import io.gromif.astracrypt.settings.aead.components.files.Files
import io.gromif.astracrypt.settings.aead.components.files.Preview

@Composable
internal fun FilesGroup(
    aeadLargeStreamTemplateList: List<String> = listOf(),
    aeadSmallStreamTemplateList: List<String> = listOf(),
    filesAeadName: String? = "Test Files SAead",
    onFilesAeadChange: (Int) -> Unit = {},
    previewAeadName: String? = "Test Preview SAead",
    onPreviewAeadChange: (Int) -> Unit = {}
) = PreferencesGroup(text = stringResource(id = R.string.files)) {
    Files(
        aeadLargeStreamTemplateList = aeadLargeStreamTemplateList,
        filesAeadName = filesAeadName,
        onFilesAeadChange = onFilesAeadChange
    )

    Preview(
        aeadSmallStreamTemplateList = aeadSmallStreamTemplateList,
        previewAeadName = previewAeadName,
        onPreviewAeadChange = onPreviewAeadChange
    )
}