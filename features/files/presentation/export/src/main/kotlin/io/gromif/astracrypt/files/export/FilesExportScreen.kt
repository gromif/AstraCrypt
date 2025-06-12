package io.gromif.astracrypt.files.export

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import contract.secureContent.SecureContentContract
import io.gromif.astracrypt.files.export.model.Params

@Composable
fun FilesExportScreen(params: Params) {
    val vm: ExportScreenViewModel = hiltViewModel()
    val secureContentState by vm.secureContentModeState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Screen(
        state = vm.uiState,
        isExternalExport = params.isExternalExport,
        onStart = {
            if (params.isExternalExport) {
                vm.export(
                    idList = params.idList.toTypedArray(),
                    output = params.outputPath!!
                ).invokeOnCompletion { vm.observeWorkInfoState() }
            } else {
                vm.export(
                    id = params.idList.first()
                )
            }
        },
        onOpenExportedFile = {
            openExportedFile(
                context = context,
                secureContentMode = secureContentState,
                uri = vm.internalExportUri
            )
        },
        onCancelExport = vm::cancelExport,
        onDispose = vm::onDispose
    )
}

private fun openExportedFile(
    context: Context,
    secureContentMode: SecureContentContract.Mode,
    uri: Uri
) {
    val intentView = Intent(Intent.ACTION_VIEW).apply {
        data = uri
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (secureContentMode != SecureContentContract.Mode.DISABLED) {
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        }
    }
    try {
        context.startActivity(intentView)
    } catch (_: Exception) {
    }
}
