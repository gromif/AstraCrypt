package com.nevidimka655.astracrypt.view.composables.lab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.composables.components.BaseNoItemsPage
import com.nevidimka655.astracrypt.view.composables.components.NoItemsPageSize
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.models.actions.help
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.compose_help.HelpItem
import com.nevidimka655.features.lab_zip.CombineZipScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

private typealias ComposableRoute = Route.LabGraph.CombinedZip

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab_combinedZip),
        actions = listOf(ToolbarActions.help)
    ),
    fab = UiState.Fab(icon = Icons.Default.FolderZip)
)

fun NavGraphBuilder.labCombinedZip(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    navigateToHelp: (List<HelpItem>) -> Unit
) = composable<ComposableRoute> {
    onUiStateChange(ScreenUiState)

    val onRequestCombiningChannel = remember { Channel<Unit>() }

    LaunchedEffect(Unit) {
        onToolbarActions.collectLatest {
            if (it == ToolbarActions.help) navigateToHelp(HelpList)
        }
    }

    LaunchedEffect(Unit) {
        onFabClick.collectLatest { onRequestCombiningChannel.send(Unit) }
    }

    CombineZipScreen(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        onRequestCombiningFlow = onRequestCombiningChannel.receiveAsFlow(),
        noItemsPage = {
            BaseNoItemsPage(
                modifier = Modifier.fillMaxSize(),
                pageSize = NoItemsPageSize.MEDIUM
            )
        }
    )
}

private val HelpList = listOf(
    HelpItem(R.string.lab_combinedZip, R.string.help_lab_zip_general)
)