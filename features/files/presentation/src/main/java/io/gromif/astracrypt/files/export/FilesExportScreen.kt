package io.gromif.astracrypt.files.export

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FilesExportScreen(params: Params) {
    val vm: ExportScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    Screen(
        state = vm.uiState,
        isExternalExport = params.isExternalExport,
        onStart = {
            if (params.isExternalExport) vm.export(
                idList = params.idList.toTypedArray(),
                output = params.outputPath!!
            ).invokeOnCompletion { vm.observeWorkInfoState() } else vm.export(
                id = params.idList.first()
            )
        },
        onOpenExportedFile = { openExportedFile(context, vm.internalExportUri) },
        onCancelExport = vm::cancelExport,
        onDispose = vm::onDispose
    )
}

private fun openExportedFile(context: Context, uri: Uri) {
    val intentView = Intent(Intent.ACTION_VIEW).apply {
        data = uri
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    try {
        context.startActivity(intentView)
    } catch (_: Exception) {}
}