package com.nevidimka655.astracrypt.view.navigation.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.Description
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.FabClickHandler
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.notes.Notes
import com.nevidimka655.notes.NotesListScreen
import com.nevidimka655.ui.compose_core.NoItemsPage
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.flow.Flow

private typealias ComposableRoute = Route.NotesGraph.List

private val NotesUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.notes)
    ),
    fab = UiState.Fab(icon = Icons.AutoMirrored.Default.NoteAdd)
)

internal fun NavGraphBuilder.notesList(
    onUiStateChange: (UiState) -> Unit,
    onFabClick: Flow<Any>,
    navigateToCreate: () -> Unit,
    navigateToView: (id: Long) -> Unit
) = composable<ComposableRoute> {
    UiStateHandler { onUiStateChange(NotesUiState) }

    FabClickHandler(onFabClick) { navigateToCreate() }

    Notes.NotesListScreen(
        onEmptyList = {
            NoItemsPage(
                mainIcon = Icons.Filled.Description,
                actionIcon = Icons.AutoMirrored.Default.NoteAdd,
                title = stringResource(R.string.noItemsTitle),
                summary = stringResource(R.string.noItemsSummary)
            )
        },
        onClick = navigateToView
    )
}