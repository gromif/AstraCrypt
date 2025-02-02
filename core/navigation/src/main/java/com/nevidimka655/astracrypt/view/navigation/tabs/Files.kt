package com.nevidimka655.astracrypt.view.navigation.tabs

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DriveFileMove
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.models.actions.createFolder
import com.nevidimka655.astracrypt.view.navigation.models.actions.delete
import com.nevidimka655.astracrypt.view.navigation.models.actions.move
import com.nevidimka655.astracrypt.view.navigation.models.actions.star
import com.nevidimka655.astracrypt.view.navigation.models.actions.unStar
import com.nevidimka655.astracrypt.view.navigation.shared.FabClickObserver
import com.nevidimka655.astracrypt.view.navigation.shared.ToolbarActionsObserver
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import com.nevidimka655.ui.compose_core.wrappers.TextWrap.Text
import io.gromif.astracrypt.files.files.FilesScreen
import io.gromif.astracrypt.files.files.model.ContextualAction
import io.gromif.astracrypt.files.files.model.Mode
import io.gromif.astracrypt.files.files.model.action.FilesNavActions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

private typealias FilesRoute = Route.Tabs.Files
private typealias StarredRoute = Route.Tabs.Starred

@Composable
private fun AnimatedContentScope.FilesSharedNavigation(
    startParentId: Long? = null,
    startParentName: String = "",
    isStarred: Boolean = false,
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>,
    navActions: FilesNavActions,
) {
    val context = LocalContext.current
    var modeState by rememberSaveable { mutableStateOf<Mode>(Mode.Default) }

    UiStateHandler(modeState) {
        val mode = modeState
        val newUiState = when (mode) {
            Mode.Default -> if (isStarred) StarredUiState else FilesUiState
            Mode.Move -> FilesMoveContextualUiState
            is Mode.Multiselect -> {
                val state = if (isStarred) StarredContextualUiState else FilesContextualUiState
                state.copy(
                    toolbar = state.toolbar.copy(
                        title = Text(
                            text = context.getString(R.string.toolbar_selected, mode.count)
                        )
                    )
                )
            }
        }
        onUiStateChange(newUiState)
    }

    val contextChannel = remember { Channel<ContextualAction>() }
    ToolbarActionsObserver(onToolbarActions) {
        val contextualAction = when {
            it === ToolbarActions.createFolder -> ContextualAction.CreateFolder
            it === ToolbarActions.star -> ContextualAction.Star(state = true)
            it === ToolbarActions.unStar -> ContextualAction.Star(state = false)
            it === ToolbarActions.delete -> ContextualAction.Delete
            it === ToolbarActions.move -> ContextualAction.MoveNavigation
            else -> ContextualAction.Close
        }
        contextChannel.send(contextualAction)
    }

    if (!isStarred) FabClickObserver(onFabClick) {
        when {
            modeState === Mode.Default -> {
                Haptic.rise()
                contextChannel.send(ContextualAction.Add)
            }
            modeState === Mode.Move -> contextChannel.send(ContextualAction.Move)
        }
    }

    FilesScreen(
        startParentId = startParentId,
        startParentName = startParentName,
        mode = modeState,
        isStarred = isStarred,
        onContextualAction = contextChannel.receiveAsFlow(),
        snackbarHostState = snackbarHostState,
        searchQueryState = searchQueryState,
        onModeChange = { modeState = it },
        navActions = navActions,
    )
}

internal fun NavGraphBuilder.tabStarred(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>,
    navActions: FilesNavActions,
) = composable<StarredRoute> {
    FilesSharedNavigation(
        isStarred = true,
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        snackbarHostState = snackbarHostState,
        searchQueryState = searchQueryState,
        navActions = navActions,
    )
}

internal fun NavGraphBuilder.tabFiles(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>,
    navActions: FilesNavActions,
) = composable<FilesRoute> {
    val route: FilesRoute = it.toRoute()
    FilesSharedNavigation(
        startParentId = route.startParentId,
        startParentName = route.startParentName,
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        snackbarHostState = snackbarHostState,
        searchQueryState = searchQueryState,
        navActions = navActions,
    )
}

private val StarredUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.starred)
    ),
    bottomBarTab = BottomBarItems.Starred
)

private val StarredContextualUiState = UiState(
    toolbar = UiState.Toolbar(
        isContextual = true,
        actions = listOf(ToolbarActions.unStar, ToolbarActions.delete)
    )
)

private val FilesUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files)
    ),
    fab = UiState.Fab(icon = Icons.Default.Add),
    bottomBarTab = BottomBarItems.Files,
    searchBar = true
)

private val FilesContextualUiState = UiState(
    toolbar = UiState.Toolbar(
        isContextual = true,
        actions = listOf(
            ToolbarActions.createFolder,
            ToolbarActions.star,
            ToolbarActions.delete,
            ToolbarActions.move,
        )
    )
)

private val FilesMoveContextualUiState = UiState(
    toolbar = UiState.Toolbar(
        isContextual = true,
        title = TextWrap.Resource(id = R.string.files_options_move)
    ),
    fab = UiState.Fab(icon = Icons.AutoMirrored.Default.DriveFileMove),
)