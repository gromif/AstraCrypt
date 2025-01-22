package io.gromif.astracrypt.settings.aead.components.database

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Preference
import com.nevidimka655.ui.compose_core.dialogs.DialogsCore
import com.nevidimka655.ui.compose_core.dialogs.radio

@Composable
internal fun Notes(
    aeadTemplatesList: List<String> = listOf(),
    notesAeadName: String? = "Test Notes Aead",
    onNotesAeadChange: (Int) -> Unit = {}
) {
    var aeadIndexToConfirm by rememberSaveable { mutableIntStateOf(0) }
    var dialogConfirmAead by DialogsCore.simple(
        title = stringResource(id = R.string.dialog_applyNewSettings),
        text = stringResource(id = R.string.dialog_applyNewSettings_message)
    ) {
        onNotesAeadChange(aeadIndexToConfirm)
    }

    val selectedIndex = remember(notesAeadName) {
        if (notesAeadName == null) 0 else aeadTemplatesList.indexOfFirst { it == notesAeadName }
    }

    var dialogDatabaseState by DialogsCore.Selectable.radio(
        onSelected = {
            aeadIndexToConfirm = it
            dialogConfirmAead = true
        },
        title = stringResource(id = R.string.notes),
        items = aeadTemplatesList,
        selectedItemIndex = selectedIndex
    )

    Preference(
        titleText = stringResource(id = R.string.notes),
        summaryText = notesAeadName ?: stringResource(R.string.withoutEncryption)
    ) { dialogDatabaseState = true }
}