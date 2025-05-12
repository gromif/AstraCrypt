package io.gromif.notes.presentation.settings.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.crypto.tink.keyset.AeadTemplate
import io.gromif.notes.domain.model.AeadMode
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.radio

@Composable
internal fun DatabaseOption(
    aeadMode: AeadMode = AeadMode.None,
    aeadTemplatesList: List<AeadTemplate> = listOf(),
    onNotesAeadChange: (AeadMode) -> Unit = {}
) {
    var aeadIndexToConfirm by remember { mutableIntStateOf(-1) }
    var dialogConfirmAead by DialogsCore.simple(
        title = stringResource(id = R.string.dialog_applyNewSettings),
        text = stringResource(id = R.string.dialog_applyNewSettings_message)
    ) {
        val aeadMode = if (aeadIndexToConfirm == -1) AeadMode.None else {
            aeadTemplatesList[aeadIndexToConfirm].let {
                AeadMode.Template(id = it.id, name = it.name)
            }
        }
        onNotesAeadChange(aeadMode)
    }

    val withoutEncryption = stringResource(R.string.withoutEncryption)
    val optionsList = remember {
        listOf(withoutEncryption) + aeadTemplatesList.map { it.name }
    }
    var dialogDatabaseState by DialogsCore.Selectable.radio(
        onSelected = {
            aeadIndexToConfirm = it - 1
            dialogConfirmAead = true
        },
        title = stringResource(id = R.string.settings_database),
        items = optionsList,
        selectedItemIndex = when (aeadMode) {
            AeadMode.None -> 0
            is AeadMode.Template -> aeadTemplatesList.indexOfFirst { it.id == aeadMode.id } + 1
        },
    )

    Preference(
        titleText = stringResource(id = R.string.notes),
        summaryText = when(aeadMode) {
            AeadMode.None -> withoutEncryption
            is AeadMode.Template -> aeadMode.name
        }
    ) { dialogDatabaseState = true }
}