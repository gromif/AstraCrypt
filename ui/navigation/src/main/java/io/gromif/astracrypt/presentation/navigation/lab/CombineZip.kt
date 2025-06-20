package io.gromif.astracrypt.presentation.navigation.lab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.help
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.compose_help.HelpItem
import io.gromif.lab_zip.presentation.CombineZipScreen
import io.gromif.ui.compose.core.BaseNoItemsPage
import io.gromif.ui.compose.core.NoItemsPageSize
import io.gromif.ui.compose.core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private typealias ComposableRoute = Route.LabGraph.CombinedZip

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab_combinedZip),
        actions = listOf(ToolbarActions.help)
    ),
    fab = UiState.Fab(icon = Icons.Default.FolderZip)
)

internal fun NavGraphBuilder.labCombinedZip() = composable<ComposableRoute> {
    val navController = LocalNavController.current
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    hostEvents.ObserveToolbarActions {
        if (it == ToolbarActions.help) {
            navController.navigate(Route.Help(helpList = Json.encodeToString(HelpList)))
        }
    }

    val onRequestCombiningChannel = remember { Channel<Unit>() }
    hostEvents.ObserveFab {
        onRequestCombiningChannel.send(Unit)
    }

    CombineZipScreen(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        onRequestCombiningFlow = onRequestCombiningChannel.receiveAsFlow(),
        noItemsPage = {
            BaseNoItemsPage(
                modifier = Modifier.fillMaxSize(),
                pageSize = NoItemsPageSize.MEDIUM,
                title = stringResource(R.string.noItemsTitle),
                summary = stringResource(R.string.noItemsSummary)
            )
        }
    )
}

private val HelpList = listOf(
    HelpItem(R.string.lab_combinedZip, R.string.help_lab_zip_general)
)
