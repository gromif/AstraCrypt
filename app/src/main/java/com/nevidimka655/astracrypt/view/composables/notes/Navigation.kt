package com.nevidimka655.astracrypt.view.composables.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

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
    val vm: NotesListViewModel = hiltViewModel()
    val items = vm.notesPaging.collectAsLazyPagingItems()
    val showEmptyPage by remember {
        derivedStateOf {
            items.itemCount == 0 && items.loadState.refresh !is LoadState.Loading
        }
    }

    LaunchedEffect(Unit) {
        onFabClick.collectLatest { navigateToCreate() }
    }

    NotesListScreen(
        noteItems = items,
        showEmptyPage = showEmptyPage,
        onClick = navigateToView
    )
}