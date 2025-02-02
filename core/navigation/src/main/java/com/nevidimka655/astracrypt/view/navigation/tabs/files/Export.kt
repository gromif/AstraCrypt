package com.nevidimka655.astracrypt.view.navigation.tabs.files

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.export.FilesExportScreen
import io.gromif.astracrypt.files.export.model.Params

private val ExportUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files_options_export)
    )
)

fun NavGraphBuilder.export(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.Export> {
    val route: Route.Export = it.toRoute()

    UiStateHandler { onUiStateChange(ExportUiState) }

    FilesExportScreen(
        params = Params(
            isExternalExport = route.isExternalExport,
            idList = listOf(route.itemId),
            outputPath = route.outUri
        )
    )
}