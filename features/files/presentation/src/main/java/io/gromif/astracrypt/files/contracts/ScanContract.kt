package io.gromif.astracrypt.files.contracts

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
internal fun scanContract(
    onResult: () -> Unit
) = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
    if (it) onResult()
}