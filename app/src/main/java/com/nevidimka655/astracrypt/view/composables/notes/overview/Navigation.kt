package com.nevidimka655.astracrypt.view.composables.notes.overview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.ToolbarAction
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.models.actions.ToolbarActionDelete
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.ui.overview.OverviewScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

private val SaveFabUiState = UiState.Fab(icon = Icons.Default.SaveAs)

fun NavGraphBuilder.overviewNote(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarAction>,
    onFabClick: Flow<Any>
) = composable<Route.NotesGraph.Overview> {
    val overview: Route.NotesGraph.Overview = it.toRoute()
    var editMode by remember { mutableStateOf(overview.noteId != -1L) }

    val onSaveRequestChannel = remember { Channel<Unit>(0) }
    val onDeleteRequestChannel = remember { Channel<Unit>(0) }

    if (editMode) LaunchedEffect(Unit) {
        onToolbarActions.collectLatest {
            if (it is ToolbarActionDelete) onDeleteRequestChannel.send(Unit)
        }
    }
    LaunchedEffect(Unit) {
        onFabClick.collectLatest {
            onSaveRequestChannel.send(Unit)
        }
    }

    var name by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

    val newName = remember(name) { if (name.length > 16) name.take(16) + "…" else name }
    val fabState = remember(name, text) {
        if (name.isNotBlank() || text.isNotBlank()) SaveFabUiState else null
    }
    onUiStateChange(
        UiState(
            toolbar = UiState.Toolbar(
                title = if (newName.isBlank() && !editMode) {
                    TextWrap.Resource(id = R.string.createNew)
                } else TextWrap.Text(text = newName),
                actions = if (editMode) listOf(ToolbarActionDelete()) else null
            ),
            fab = fabState
        )
    )

    Notes.OverviewScreen(
        noteId = overview.noteId,
        editMode = editMode,
        onSaveRequestChannel = onSaveRequestChannel,
        onDeleteRequestChannel = onDeleteRequestChannel,
        onChangeName = { name = it },
        onChangeText = { text = it },
        nameFieldLabel = stringResource(id = R.string.note_title),
        textFieldLabel = stringResource(id = R.string.text)
    )
}