package com.nevidimka655.astracrypt.view.composables.notes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.composables.notes.overview.overviewNote
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.notesGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onFabClick: Flow<Any>
) = navigation<Route.NotesGraph>(startDestination = Route.NotesGraph.List) {
    notesList(
        onUiStateChange = onUiStateChange, onFabClick = onFabClick,
        navigateToCreate = {
            navController.navigate(Route.NotesGraph.Overview())
        },
        navigateToView = { navController.navigate(Route.NotesGraph.Overview(noteId = it)) }
    )
    overviewNote(onUiStateChange = onUiStateChange, onFabClick = onFabClick)
}