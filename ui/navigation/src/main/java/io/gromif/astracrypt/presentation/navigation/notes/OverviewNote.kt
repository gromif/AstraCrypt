package io.gromif.astracrypt.presentation.navigation.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.delete
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.notes.presentation.Notes
import io.gromif.notes.presentation.overview.OverviewScreen
import io.gromif.ui.compose.core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel

private val SaveFabUiState = UiState.Fab(icon = Icons.Default.SaveAs)

internal fun NavGraphBuilder.overviewNote() = composable<Route.NotesGraph.Overview> {
    val hostEvents = LocalHostEvents.current

    val overview: Route.NotesGraph.Overview = it.toRoute()
    var editMode by remember { mutableStateOf(overview.noteId != -1L) }

    val onSaveRequestChannel = remember { Channel<Unit>(0) }
    val onDeleteRequestChannel = remember { Channel<Unit>(0) }

    if (editMode) hostEvents.ObserveToolbarActions {
        if (it == ToolbarActions.delete) onDeleteRequestChannel.send(Unit)
    }
    hostEvents.ObserveFab {
        onSaveRequestChannel.send(Unit)
    }

    var name by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    val newName = remember(name) { if (name.length > 16) name.take(16) + "…" else name }
    UiStateHandler(name, text) {
        val newState = UiState(
            toolbar = UiState.Toolbar(
                title = if (newName.isBlank() && !editMode) {
                    TextWrap.Resource(id = R.string.createNew)
                } else TextWrap.Text(text = newName),
                actions = if (editMode) listOf(ToolbarActions.delete) else null
            ),
            fab = if (name.isNotBlank() || text.isNotBlank()) SaveFabUiState else null
        )
        hostEvents.setUiState(newState)
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