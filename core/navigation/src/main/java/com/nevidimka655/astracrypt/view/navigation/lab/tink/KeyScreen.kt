package com.nevidimka655.astracrypt.view.navigation.lab.tink

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.FabClickHandler
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.tink_lab.TinkLab
import com.nevidimka655.tink_lab.key.KeyScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
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
    FabClickHandler(onFabClick) { onRequestKeysetChannel.send(Unit) }

    TinkLab.KeyScreen(
        onRequestKeysetChannel = onRequestKeysetChannel.receiveAsFlow(),
        navigateToTextMode = navigateToTextMode,
        navigateToFilesMode = navigateToFilesMode
    )
}