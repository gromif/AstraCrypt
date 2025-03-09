package io.gromif.astracrypt.presentation.navigation.tabs.files

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import io.gromif.astracrypt.files.export.FilesExportScreen
import io.gromif.astracrypt.files.export.model.Params
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.ui.compose.core.wrappers.TextWrap

private val ExportUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files_options_export)
    )
)

internal fun NavGraphBuilder.export() = composable<Route.Export> {
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(ExportUiState) }

    val route: Route.Export = it.toRoute()

    FilesExportScreen(
        params = Params(
            isExternalExport = route.isExternalExport,
            idList = listOf(route.itemId),
            outputPath = route.outUri
        )
    )
}