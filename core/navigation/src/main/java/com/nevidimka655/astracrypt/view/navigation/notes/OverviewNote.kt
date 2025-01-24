package com.nevidimka655.astracrypt.view.navigation.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.models.actions.delete
import com.nevidimka655.astracrypt.view.navigation.shared.FabClickObserver
import com.nevidimka655.astracrypt.view.navigation.shared.ToolbarActionsObserver
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.overview.OverviewScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

private val SaveFabUiState = UiState.Fab(icon = Icons.Default.SaveAs)

internal fun NavGraphBuilder.overviewNote(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>
) = composable<Route.NotesGraph.Overview> {
    val overview: Route.NotesGraph.Overview = it.toRoute()
    var editMode by remember { mutableStateOf(overview.noteId != -1L) }

    val onSaveRequestChannel = remember { Channel<Unit>(0) }
    val onDeleteRequestChannel = remember { Channel<Unit>(0) }

    if (editMode) ToolbarActionsObserver(onToolbarActions) {
        if (it == ToolbarActions.delete) onDeleteRequestChannel.send(Unit)
    }
    FabClickObserver(onFabClick) {
        onSaveRequestChannel.send(Unit)
    }

    var name by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    val newName = remember(name) { if (name.length > 16) name.take(16) + "…" else name }
    val fabState = remember(name, text) {
        if (name.isNotBlank() || text.isNotBlank()) SaveFabUiState else null
    }
    UiStateHandler {
        val newState = UiState(
            toolbar = UiState.Toolbar(
                title = if (newName.isBlank() && !editMode) {
                    TextWrap.Resource(id = R.string.createNew)
                } else TextWrap.Text(text = newName),
                actions = if (editMode) listOf(ToolbarActions.delete) else null
            ),
            fab = fabState
        )
        onUiStateChange(newState)
    }

    Notes.OverviewScreen(
        noteId = overview.noteId,
        editMode = editMode,
        onSaveRequestChannel = onSaveRequestChannel,
        onDeleteRequestChannel = onDeleteRequestChannel,
        onChangeName = { name = it },
        onChangeText = { text = it }
    )
}