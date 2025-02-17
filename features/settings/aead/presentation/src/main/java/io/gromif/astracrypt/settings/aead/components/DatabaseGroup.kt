package io.gromif.astracrypt.settings.aead.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.PreferencesGroup

@Composable
internal fun DatabaseGroup(
    aeadTemplatesList: List<String> = listOf(),
    notesAeadName: String? = "Test Notes Aead",
    onNotesAeadChange: (Int) -> Unit = {}
) = PreferencesGroup(text = stringResource(id = R.string.settings_database)) {
    /*val dbEncryption = remember(aeadInfo) {
        encryptionManager.getDbEncryptionName() ?: getString(R.string.withoutEncryption)
    }
    var selectedDbEncryptionToConfirm by rememberSaveable { mutableIntStateOf(-1) }
    var dbEncryptConfirmation by Dialogs.simple(
        title = stringResource(id = R.string.dialog_applyNewSettings),
        text = stringResource(id = R.string.dialog_applyNewSettings_message)
    ) {
        scope.launch {
            onDatabaseEncryptionSelected(which = selectedDbEncryptionToConfirm)
        }
    }*/

    /*var dialogDatabaseState by Dialogs.Selectable.radio(
        onSelected = {
            selectedDbEncryptionToConfirm = it
            dbEncryptConfirmation = true
        },
        title = stringResource(id = R.string.settings_database),
        items = aeadTemplatesList,
        selectedItemIndex = run {
            val name = encryptionManager.getDbEncryptionName()
            if (name != null) KeysetTemplates.AEAD.valueOf(name).ordinal + 1
            else 0
        }
    )
    Preference(
        titleText = stringResource(id = R.string.settings_database),
        summaryText = dbEncryption
    ) {
        dialogDatabaseState = true
    }
    val columnsEncrypted = remember(aeadInfo) {
        val prefixText = getString(R.string.settings_columns_summary)
        val suffix = with(aeadInfo) {
            val str = StringBuilder()
            fun append(value: String) = str.append("$value, ")
            if (isNameEncrypted) append(getString(R.string.name))
            if (isThumbnailEncrypted) append(getString(R.string.thumbnail))
            if (isPathEncrypted) append(getString(R.string.path))
            if (isFlagsEncrypted) append(getString(R.string.files_options_details))
            if (isEncryptionTypeEncrypted) append(getString(R.string.encryption_type))
            if (isThumbEncryptionTypeEncrypted) append(getString(R.string.thumb_encryption_type))
            str.removeSuffix(", ")
        }.toString().lowercase()
        "$prefixText: $suffix"
    }
    Preference(
        titleText = stringResource(id = R.string.settings_columns),
        summaryText = columnsEncrypted
    ) {
        findNavController().navigate(R.id.action_encryptionFragment_to_databaseColumnsFragment)
    }*/
}