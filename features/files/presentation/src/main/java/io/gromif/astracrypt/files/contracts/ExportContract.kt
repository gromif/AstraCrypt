package io.gromif.astracrypt.files.contracts

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
internal fun Contracts.export(
    onResult: (Uri) -> Unit
) = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
    it?.let { onResult(it) }
}