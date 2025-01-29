package io.gromif.astracrypt.profile.presentation.settings.contracts

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
internal fun Contracts.externalAvatar(
    onResult: (Uri) -> Unit
) = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
    if (it != null) onResult(it)
}