package com.nevidimka655.astracrypt.view.composables.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.composables.components.NoItemsPage
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.ui.NotesListScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

val NotesListUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.notes)
    ),
    fab = UiState.Fab(icon = Icons.AutoMirrored.Default.NoteAdd)
)

fun NavGraphBuilder.notesList(
    onUiStateChange: (UiState) -> Unit,
    onFabClick: Flow<Any>,
    navigateToCreate: () -> Unit,
    navigateToView: (id: Long) -> Unit
) = composable<Route.NotesGraph.List> {
    onUiStateChange(NotesListUiState)

    LaunchedEffect(Unit) {
        onFabClick.collectLatest { navigateToCreate() }
    }

    Notes.NotesListScreen(
        onEmptyList = {
            NoItemsPage(
                mainIcon = Icons.Filled.Description,
                actionIcon = Icons.AutoMirrored.Default.NoteAdd
            )
        },
        onClick = navigateToView
    )
}