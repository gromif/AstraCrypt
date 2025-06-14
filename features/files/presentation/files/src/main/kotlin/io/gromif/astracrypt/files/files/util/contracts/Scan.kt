package io.gromif.astracrypt.files.files.util.contracts

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
internal fun Contracts.scan(
    onResult: () -> Unit
) = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
    if (it) onResult()
}
