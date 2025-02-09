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
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.overview.OverviewScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.delete
import io.gromif.astracrypt.presentation.navigation.shared.FabClickObserver
import io.gromif.astracrypt.presentation.navigation.shared.ToolbarActionsObserver
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
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