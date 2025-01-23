package com.nevidimka655.astracrypt.view.navigation.tabs

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.models.actions.createFolder
import com.nevidimka655.astracrypt.view.navigation.models.actions.delete
import com.nevidimka655.astracrypt.view.navigation.models.actions.star
import com.nevidimka655.astracrypt.view.navigation.models.actions.unStar
import com.nevidimka655.astracrypt.view.navigation.shared.FabClickHandler
import com.nevidimka655.astracrypt.view.navigation.shared.ToolbarActionsHandler
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.FilesScreen
import io.gromif.astracrypt.files.model.ContextualAction
import io.gromif.astracrypt.files.model.Mode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

private typealias FilesRoute = Route.Tabs.Files
private typealias StarredRoute = Route.Tabs.Starred

@Composable
private fun AnimatedContentScope.FilesSharedNavigation(
    isStarred: Boolean = false,
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    searchQueryState: StateFlow<String>,
) {
    val context = LocalContext.current
    var modeState by rememberSaveable { mutableStateOf<Mode>(Mode.Default) }

    UiStateHandler(modeState) {
        val mode = modeState
        val newUiState = when (mode) {
            Mode.Default -> if (isStarred) StarredUiState else FilesUiState
            is Mode.Multiselect -> {
                val state = if (isStarred) StarredContextualUiState else FilesContextualUiState
                state.copy(
                    toolbar = state.toolbar.copy(
                        title = TextWrap.Text(
                            text = context.getString(R.string.toolbar_selected, mode.count)
                        )
                    )
                )
            }
        }
        onUiStateChange(newUiState)
    }

    val contextChannel = remember { Channel<ContextualAction>() }
    ToolbarActionsHandler(onToolbarActions) {
        when {
            it === ToolbarActions.createFolder -> contextChannel.send(ContextualAction.CreateFolder)
            it === ToolbarActions.star -> contextChannel.send(ContextualAction.Star)
            it === ToolbarActions.unStar -> contextChannel.send(ContextualAction.Unstar)
            it === ToolbarActions.delete -> contextChannel.send(ContextualAction.Delete)
        }
    }

    val sheetCreateState = Compose.state()
    if (!isStarred) FabClickHandler(onFabClick) {
        Haptic.rise()
        sheetCreateState.value = true
    }

    FilesScreen(
        isStarred = isStarred,
        onContextualAction = contextChannel.receiveAsFlow(),
        searchQueryState = searchQueryState,
        onModeChange = { modeState = it },
        toExport = { id, exportUri ->

        },
        toDetails = {},
        sheetCreateState = sheetCreateState
    )
}

fun NavGraphBuilder.tabStarred(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    searchQueryState: StateFlow<String>,
) = composable<StarredRoute> {
    FilesSharedNavigation(
        isStarred = true,
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        searchQueryState = searchQueryState
    )
}

fun NavGraphBuilder.tabFiles(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    searchQueryState: StateFlow<String>,
) = composable<FilesRoute> {
    FilesSharedNavigation(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        searchQueryState = searchQueryState
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
        )
    )
)