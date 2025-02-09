package io.gromif.astracrypt.presentation.navigation.notes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.notesGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>
) = navigation<Route.NotesGraph>(startDestination = Route.NotesGraph.List) {
    notesList(
        onUiStateChange = onUiStateChange, onFabClick = onFabClick,
        navigateToCreate = {
            navController.navigate(Route.NotesGraph.Overview())
        },
        navigateToView = { navController.navigate(Route.NotesGraph.Overview(noteId = it)) }
    )
    overviewNote(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick
    )
}