package com.nevidimka655.astracrypt.view.composables.lab.tink

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.tink_lab.TinkLabKeyScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel

@Suppress("ObjectPropertyName")
val _LabTinkUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab_aeadEncryption)
    ),
    fab = UiState.Fab(icon = Icons.Default.Done)
)

fun NavGraphBuilder.tinkKey(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.LabGraph.TinkGraph.Key> {
    onUiStateChange(_LabTinkUiState)
    val onRequestKeysetChannel = remember { Channel<Unit>() }

    TinkLabKeyScreen(
        onRequestKeysetChannel = onRequestKeysetChannel,
        onFinish = {

        }
    )
}