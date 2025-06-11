package io.gromif.astracrypt.files.settings.aead.database

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.radio

@Preview(showBackground = true)
@Composable
internal fun DatabaseGroup(
    aeadInfo: AeadInfo = AeadInfo(),
    databaseOptions: List<String> = listOf("TEST_ENCRYPTION"),
    databaseAeadIndex: Int = 0,
    onDatabaseAeadChange: (Int) -> Unit = {},
    toDatabaseColumnsAeadSettings: () -> Unit = {},
) = PreferencesGroup(text = stringResource(id = R.string.settings_database)) {
    val context = LocalContext.current

    var selectedDbEncryptionToConfirm by rememberSaveable { mutableIntStateOf(-1) }
    var dbEncryptConfirmation by DialogsCore.simple(
        title = stringResource(id = R.string.dialog_applyNewSettings),
        text = stringResource(id = R.string.dialog_applyNewSettings_message)
    ) { onDatabaseAeadChange(selectedDbEncryptionToConfirm) }

    var dialogDatabaseState by DialogsCore.Selectable.radio(
        onSelected = {
            selectedDbEncryptionToConfirm = it
            dbEncryptConfirmation = true
        },
        title = stringResource(id = R.string.settings_database),
        items = databaseOptions,
        selectedItemIndex = databaseAeadIndex
    )
    Preference(
        titleText = stringResource(id = R.string.settings_database),
        summaryText = databaseOptions[databaseAeadIndex]
    ) { dialogDatabaseState = true }

    val columnsTextPrefix = stringResource(R.string.settings_columns_summary)
    val columnsEncrypted = remember(aeadInfo) {
        buildString {
            fun append(@StringRes id: Int) = append("${context.getString(id).lowercase()}, ")

            append("$columnsTextPrefix: ")
            if (aeadInfo.name) append(id = R.string.name)
            if (aeadInfo.preview) append(id = R.string.thumbnail)
            if (aeadInfo.file) append(id = R.string.path)
            if (aeadInfo.flag) append(id = R.string.files_options_details)
        }.removeSuffix(", ")
    }
    Preference(
        titleText = stringResource(id = R.string.settings_columns),
        summaryText = columnsEncrypted,
        callback = toDatabaseColumnsAeadSettings,
    )
}
