package io.gromif.astracrypt.presentation.navigation.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.notes.presentation.Notes
import io.gromif.notes.presentation.NotesListScreen
import io.gromif.ui.compose.core.NoItemsPage
import io.gromif.ui.compose.core.wrappers.TextWrap

private typealias ComposableRoute = Route.NotesGraph.List

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.notes)
    ),
    fab = UiState.Fab(icon = Icons.AutoMirrored.Default.NoteAdd)
)

internal fun NavGraphBuilder.notesList() = composable<ComposableRoute> {
    val navController = LocalNavController.current
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    hostEvents.ObserveFab {
        navController.navigate(Route.NotesGraph.Overview())
    }

    Notes.NotesListScreen(
        onEmptyList = {
            NoItemsPage(
                mainIcon = Icons.Filled.Description,
                actionIcon = Icons.AutoMirrored.Default.NoteAdd,
                title = stringResource(R.string.noItemsTitle),
                summary = stringResource(R.string.noItemsSummary)
            )
        },
        onClick = {
            navController.navigate(Route.NotesGraph.Overview(noteId = it))
        }
    )
}