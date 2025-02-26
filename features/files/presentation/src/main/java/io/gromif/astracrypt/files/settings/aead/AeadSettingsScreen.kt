package io.gromif.astracrypt.files.settings.aead

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.astracrypt.files.settings.aead.database.DatabaseGroup
import io.gromif.astracrypt.files.settings.aead.files.FilesGroup

@Preview(showBackground = true)
@Composable
internal fun AeadSettingsScreen(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
    params: AeadSettingsScreenParams = AeadSettingsScreenParams(),
    onFileAeadChange: (Int) -> Unit = {},
    onPreviewAeadChange: (Int) -> Unit = {},
    onDatabaseAeadChange: (Int) -> Unit = {},
    toDatabaseColumnsAeadSettings: () -> Unit = {},
) = Column(
    modifier = modifier,
    verticalArrangement = verticalArrangement
) {
    FilesGroup(
        fileOptions = params.fileOptions,
        fileAeadIndex = params.fileAeadIndex,
        onFileAeadChange = onFileAeadChange,

        previewOptions = params.previewOptions,
        previewAeadIndex = params.previewAeadIndex,
        onPreviewAeadChange = onPreviewAeadChange
    )
    DatabaseGroup(
        aeadInfo = params.aeadInfo,
        databaseOptions = params.databaseOptions,
        databaseAeadIndex = params.databaseAeadIndex,
        onDatabaseAeadChange = onDatabaseAeadChange,
        toDatabaseColumnsAeadSettings = toDatabaseColumnsAeadSettings
    )
}