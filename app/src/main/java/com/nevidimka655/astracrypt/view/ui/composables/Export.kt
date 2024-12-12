package com.nevidimka655.astracrypt.view.ui.composables

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.export.ExportScreen
import com.nevidimka655.astracrypt.features.export.ExportScreenViewModel
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val ExportUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files_options_export)
    )
)

inline fun NavGraphBuilder.export(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.Export> {
    val export: Route.Export = it.toRoute()
    onUiStateChange(ExportUiState)
    val vm: ExportScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    ExportScreen(
        state = vm.uiState,
        isExternalExport = export.outUri != null,
        onStart = {
            if (export.outUri != null) vm.export(
                itemId = export.itemId,
                output = export.outUri
            ).invokeOnCompletion { vm.observeWorkInfoState() } else vm.export(
                itemId = export.itemId,
                contentResolver = context.contentResolver
            )
        },
        onOpenExportedFile = { vm.openExportedFile(context = context) },
        onCancelExport = { vm.cancelExport() },
        onDispose = { vm.onDispose() }
    )
}