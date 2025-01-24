package com.nevidimka655.astracrypt.view.navigation.lab

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
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.models.actions.help
import com.nevidimka655.astracrypt.view.navigation.shared.FabClickObserver
import com.nevidimka655.astracrypt.view.navigation.shared.ToolbarActionsHandler
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.compose_help.HelpItem
import com.nevidimka655.features.lab_zip.CombineZipScreen
import com.nevidimka655.ui.compose_core.BaseNoItemsPage
import com.nevidimka655.ui.compose_core.NoItemsPageSize
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

private typealias ComposableRoute = Route.LabGraph.CombinedZip

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab_combinedZip),
        actions = listOf(ToolbarActions.help)
    ),
    fab = UiState.Fab(icon = Icons.Default.FolderZip)
)

internal fun NavGraphBuilder.labCombinedZip(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    navigateToHelp: (List<HelpItem>) -> Unit
) = composable<ComposableRoute> {
    UiStateHandler { onUiStateChange(ScreenUiState) }
    ToolbarActionsHandler(onToolbarActions) {
        if (it == ToolbarActions.help) navigateToHelp(HelpList)
    }

    val onRequestCombiningChannel = remember { Channel<Unit>() }
    FabClickObserver(onFabClick) {
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