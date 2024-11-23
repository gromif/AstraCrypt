package com.nevidimka655.astracrypt.ui.dialogs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.tabs.Tabs
import com.nevidimka655.ui.compose_core.dialogs.DialogDefaults
import com.nevidimka655.ui.compose_core.dialogs.DialogWithNewButtonBar

@Composable
fun Tabs.Files.Dialogs.DeleteFile(
    state: MutableState<Boolean>,
    name: String,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    DialogWithNewButtonBar(
        title = "${context.getString(R.string.files_options_delete)}?",
        onDismissRequest = { state.value = false },
        onConfirmRequest = onDelete,
        buttonBar = DialogDefaults.newButtonBar(
            onConfirmClick = {
                state.value = false
                onDelete()
            },
            onDismissClick = { state.value = false },
            isConfirmButtonEnabled = true,
            confirmText = context.getString(R.string.files_options_delete),
            confirmIcon = Icons.Default.DeleteForever,
            dismissText = context.getString(android.R.string.cancel),
            dismissIcon = Icons.Default.Close
        )
    ) {
        Text(text = "${context.getString(R.string.files_options_delete)} \"$name\"?")
    }
}