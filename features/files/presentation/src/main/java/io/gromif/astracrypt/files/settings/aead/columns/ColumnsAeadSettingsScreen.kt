package io.gromif.astracrypt.files.settings.aead.columns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.CheckBoxOneLineListItem
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen
import io.gromif.ui.compose.core.dialogs.DialogsCore

@Preview(showBackground = true)
@Composable
internal fun ColumnsAeadSettingsScreen(
    params: Params = Params(),
    onSetName: (Boolean) -> Unit = {},
    onSetPreview: (Boolean) -> Unit = {},
    onSetFile: (Boolean) -> Unit = {},
    onSetFlag: (Boolean) -> Unit = {},
) = PreferencesScreen {
    PreferencesGroup {
        var dialogNameConfirmation by DialogsCore.simple(
            title = stringResource(id = R.string.dialog_encryptName),
            text = stringResource(id = R.string.dialog_encryptName_msg)
        ) { onSetName(true) }
        CheckBoxOneLineListItem(
            isChecked = params.name,
            titleText = stringResource(id = R.string.name)
        ) {
            if (it) dialogNameConfirmation = true
            else onSetName(false)
        }
        CheckBoxOneLineListItem(
            isChecked = params.preview,
            titleText = stringResource(id = R.string.thumbnail),
            onChange = onSetPreview
        )
        CheckBoxOneLineListItem(
            isChecked = params.file,
            titleText = stringResource(id = R.string.path),
            onChange = onSetFile
        )
        CheckBoxOneLineListItem(
            isChecked = params.flag,
            titleText = stringResource(id = R.string.files_options_details),
            onChange = onSetFlag
        )
    }
}