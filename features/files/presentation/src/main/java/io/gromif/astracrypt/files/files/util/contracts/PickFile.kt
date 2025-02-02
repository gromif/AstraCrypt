package io.gromif.astracrypt.files.files.util.contracts

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
internal fun Contracts.pickFile(
    onResult: (List<Uri>) -> Unit
) = rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) {
    if (it.isNotEmpty()) onResult(it)
}