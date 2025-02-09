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
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.compose_help.HelpItem
import com.nevidimka655.features.lab_zip.CombineZipScreen
import com.nevidimka655.ui.compose_core.BaseNoItemsPage
import com.nevidimka655.ui.compose_core.NoItemsPageSize
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.help
import io.gromif.astracrypt.presentation.navigation.shared.FabClickObserver
import io.gromif.astracrypt.presentation.navigation.shared.ToolbarActionsObserver
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
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
    ToolbarActionsObserver(onToolbarActions) {
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