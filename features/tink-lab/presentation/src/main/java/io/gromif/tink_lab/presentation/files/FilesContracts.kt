package io.gromif.tink_lab.presentation.files

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object FilesContracts {

    @Composable
    fun openInput(onResult: (Uri) -> Unit) = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { if (it != null) onResult(it) }

    @Composable
    fun openOutputDir(onResult: (Uri) -> Unit) = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { if (it != null) onResult(it) }

}