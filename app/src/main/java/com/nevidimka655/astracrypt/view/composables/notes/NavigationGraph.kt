package com.nevidimka655.astracrypt.view.composables.notes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import kotlinx.coroutines.channels.Channel

inline fun NavGraphBuilder.notesGraph(
    crossinline onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onFabClick: Channel<Any>
) = navigation<Route.NotesGraph>(startDestination = Route.NotesGraph.NotesList) {
    notesList(onUiStateChange = onUiStateChange)
}