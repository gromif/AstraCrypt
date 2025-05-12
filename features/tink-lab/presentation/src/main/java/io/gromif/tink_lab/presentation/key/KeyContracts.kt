package io.gromif.tink_lab.presentation.key

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object KeyContracts {

    @Composable
    fun open(onResult: (Uri) -> Unit) = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { if (it != null) onResult(it) }

    @Composable
    fun save(onResult: (Uri) -> Unit) = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { if (it != null) onResult(it) }

}