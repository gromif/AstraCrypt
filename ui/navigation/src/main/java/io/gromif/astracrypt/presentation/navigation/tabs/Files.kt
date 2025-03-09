package io.gromif.astracrypt.presentation.navigation.tabs

import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DriveFileMove
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
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import io.gromif.astracrypt.files.files.FilesScreen
import io.gromif.astracrypt.files.files.model.ContextualAction
import io.gromif.astracrypt.files.files.model.Mode
import io.gromif.astracrypt.files.files.model.action.FilesNavActions
import io.gromif.astracrypt.presentation.navigation.BottomBarItems
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.createFolder
import io.gromif.astracrypt.presentation.navigation.models.actions.delete
import io.gromif.astracrypt.presentation.navigation.models.actions.move
import io.gromif.astracrypt.presentation.navigation.models.actions.star
import io.gromif.astracrypt.presentation.navigation.models.actions.unStar
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostStateHolder
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.ui.compose.core.wrappers.TextWrap
import io.gromif.ui.compose.core.wrappers.TextWrap.Text
import io.gromif.ui.haptic.Haptic
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

private typealias FilesRoute = Route.Tabs.Files
private typealias StarredRoute = Route.Tabs.Starred

internal fun NavGraphBuilder.tabStarred() = composable<StarredRoute> {
    FilesSharedNavigation(isStarred = true)
}

internal fun NavGraphBuilder.tabFiles() = composable<FilesRoute> {
    val route: FilesRoute = it.toRoute()
    FilesSharedNavigation(
        startParentId = route.startParentId,
        startParentName = route.startParentName,
    )
}

@Composable
private fun AnimatedContentScope.FilesSharedNavigation(
    startParentId: Long? = null,
    startParentName: String = "",
    isStarred: Boolean = false,
) {
    val navController = LocalNavController.current
    val hostStateHolder = LocalHostStateHolder.current
    val hostEvents = LocalHostEvents.current
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
        hostEvents.setUiState(newUiState)
    }

    val contextChannel = remember { Channel<ContextualAction>() }
    hostEvents.ObserveToolbarActions {
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

    if (!isStarred) hostEvents.ObserveFab {
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
        snackbarHostState = hostStateHolder.snackbarHostState,
        searchQueryState = hostStateHolder.searchQueryState,
        onModeChange = { modeState = it },
        navActions = object : FilesNavActions {
            override fun toFiles(id: Long, name: String) = with(navController) {
                clearBackStack(Route.Tabs.Files())
                navigate(Route.Tabs.Files(startParentId = id, startParentName = name)) {
                    launchSingleTop = true
                    popUpTo(Route.Tabs.Home) { saveState = true }
                }
            }

            override fun toExport(id: Long, output: Uri) {
                navController.navigate(
                    Route.Export(
                        isExternalExport = true,
                        itemId = id,
                        outUri = output.toString()
                    ))
            }

            override fun toExportPrivately(id: Long) {
                navController.navigate(
                    Route.Export(
                        isExternalExport = false,
                        itemId = id
                    ))
            }

            override fun toDetails(id: Long) {
                navController.navigate(Route.Details(id = id))
            }
        },
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