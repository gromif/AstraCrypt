package io.gromif.astracrypt.presentation.navigation.notes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import io.gromif.astracrypt.presentation.navigation.Route

internal fun NavGraphBuilder.notesGraph() = navigation<Route.NotesGraph>(startDestination = Route.NotesGraph.List) {
    notesList()
    overviewNote()
}