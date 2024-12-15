package com.nevidimka655.astracrypt.view.composables.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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

val NotesListUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.notes)
    ),
    fab = UiState.Fab(icon = Icons.Default.Edit)
)

inline fun NavGraphBuilder.notesList(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.NotesGraph.NotesList> {
    onUiStateChange(NotesListUiState)
    val vm: NotesListViewModel = hiltViewModel()
    val items = vm.notesPaging.collectAsLazyPagingItems()
    val showEmptyPage by remember {
        derivedStateOf {
            items.itemCount == 0 && items.loadState.refresh !is LoadState.Loading
        }
    }

    NotesListScreen(
        noteItems = items,
        showEmptyPage = showEmptyPage
    )
}