package io.gromif.astracrypt.presentation.navigation.lab.tink

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.tink_lab.TinkLab
import com.nevidimka655.tink_lab.key.KeyScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.FabClickObserver
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab_aeadEncryption)
    ),
    fab = UiState.Fab(icon = Icons.Default.Done)
)

internal fun NavGraphBuilder.tinkKey(
    onUiStateChange: (UiState) -> Unit,
    onFabClick: Flow<Any>,
    navigateToTextMode: (String) -> Unit,
    navigateToFilesMode: (String) -> Unit
) = composable<Route.LabGraph.TinkGraph.Key> {
    UiStateHandler { onUiStateChange(ScreenUiState) }

    val onRequestKeysetChannel = remember { Channel<Unit>() }
    FabClickObserver(onFabClick) { onRequestKeysetChannel.send(Unit) }

    TinkLab.KeyScreen(
        onRequestKeysetChannel = onRequestKeysetChannel.receiveAsFlow(),
        navigateToTextMode = navigateToTextMode,
        navigateToFilesMode = navigateToFilesMode
    )
}