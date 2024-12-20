package com.nevidimka655.astracrypt.view.composables.notes.overview

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.ToolbarAction
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.models.actions.ToolbarActionDelete
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.ui.OverviewScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val SaveFabUiState = UiState.Fab(icon = Icons.Default.SaveAs)

fun NavGraphBuilder.overviewNote(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarAction>,
    onFabClick: Flow<Any>
) = composable<Route.NotesGraph.Overview> {
    val overview: Route.NotesGraph.Overview = it.toRoute()
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val vm: OverviewNoteViewModel = hiltViewModel()
    val name by vm.nameState.collectAsStateWithLifecycle()
    val text by vm.textState.collectAsStateWithLifecycle()
    var editMode by remember { mutableStateOf(overview.noteId != -1L) }

    if (editMode) LaunchedEffect(Unit) {
        launch { vm.load(id = overview.noteId)  }
        launch {
            onToolbarActions.collectLatest {
                if (it is ToolbarActionDelete) vm.delete().invokeOnCompletion {
                    backDispatcher?.onBackPressed()
                }
            }
        }
    }

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

    LaunchedEffect(Unit) {
        onFabClick.collectLatest {
            vm.save().invokeOnCompletion {
                backDispatcher?.onBackPressed()
            }
        }
    }


    Notes.OverviewScreen(
        nameFieldLabel = stringResource(id = R.string.note_title),
        name = name,
        onChangeName = { if (it.length <= 64) vm.setName(name = it) },
        textFieldLabel = stringResource(id = R.string.text),
        text = text,
        onChangeText = { if (it.length <= 1000) vm.setText(text = it) }
    )
}