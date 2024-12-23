package com.nevidimka655.astracrypt.view.composables.components.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.dialogs.DialogDefaults
import com.nevidimka655.ui.compose_core.dialogs.DialogWithNewButtonBar

@Composable
fun Dialogs.DeleteOriginalFiles(
    onImportStartDelete: () -> Unit,
    onImportStartSave: () -> Unit
) {
    val context = LocalContext.current
    DialogWithNewButtonBar(
        title = context.getString(R.string.dialog_deleteOriginalFiles),
        onDismissRequest = {  },
        onConfirmRequest = onImportStartDelete,
        buttonBar = DialogDefaults.newButtonBar(
            onConfirmClick = onImportStartDelete,
            onDismissClick = onImportStartSave,
            isConfirmButtonEnabled = true,
            confirmText = context.getString(R.string.files_options_delete),
            confirmIcon = Icons.Default.DeleteForever,
            dismissText = context.getString(R.string.save),
            dismissIcon = Icons.Default.Close
        )
    ) {
        Text(text = context.getString(R.string.dialog_deleteOriginalFiles_msg))
    }
}