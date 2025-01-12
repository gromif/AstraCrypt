package io.gromif.astracrypt.files.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.dialogs.DialogDefaults
import com.nevidimka655.ui.compose_core.dialogs.DialogWithNewButtonBar

@Composable
internal fun deleteSourceDialog(
    onResult: (Boolean) -> Unit,
): MutableState<Boolean> {
    val dialogDeleteSourceState = Compose.state()

    fun sendResult(saveSource: Boolean) {
        dialogDeleteSourceState.value = false
        onResult(saveSource)
    }

    if (dialogDeleteSourceState.value) DialogWithNewButtonBar(
        title = stringResource(id = R.string.dialog_deleteOriginalFiles),
        onDismissRequest = {},
        onConfirmRequest = { sendResult(saveSource = false) },
        buttonBar = DialogDefaults.newButtonBar(
            onConfirmClick = { sendResult(saveSource = false) },
            onDismissClick = { sendResult(saveSource = true) },
            isConfirmButtonEnabled = true,
            confirmText = stringResource(id = R.string.files_options_delete),
            confirmIcon = Icons.Default.DeleteForever,
            dismissText = stringResource(id = R.string.save),
            dismissIcon = Icons.Default.Close
        )
    ) {
        Text(text = stringResource(id = R.string.dialog_deleteOriginalFiles_msg))
    }
    return dialogDeleteSourceState
}