package io.gromif.astracrypt.presentation.navigation.lab.tink

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostStateHolder
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.tinkLab.presentation.TinkLab
import io.gromif.tinkLab.presentation.key.KeyScreen
import io.gromif.ui.compose.core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

private typealias Text = Route.LabGraph.TinkGraph.Text
private typealias Files = Route.LabGraph.TinkGraph.Files

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab_aeadEncryption)
    ),
    fab = UiState.Fab(icon = Icons.Default.Done)
)

internal fun NavGraphBuilder.tinkKey() = composable<Route.LabGraph.TinkGraph.Key> {
    val navController = LocalNavController.current
    val hostEvents = LocalHostEvents.current
    val hostState = LocalHostStateHolder.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    val onRequestKeysetChannel = remember { Channel<Unit>() }
    hostEvents.ObserveFab {
        onRequestKeysetChannel.send(Unit)
    }

    val messageInvalidPassword = stringResource(id = R.string.t_invalidPass)

    TinkLab.KeyScreen(
        onRequestKeysetChannel = onRequestKeysetChannel.receiveAsFlow(),
        onInvalidPassword = {
            hostState.snackbarHostState.showSnackbar(messageInvalidPassword)
        },
        navigateToTextMode = { navController.navigate(Text(rawKeyset = it)) },
        navigateToFilesMode = { navController.navigate(Files(rawKeyset = it)) }
    )
}
