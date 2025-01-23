package com.nevidimka655.astracrypt.view.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.FilesScreen
import io.gromif.astracrypt.files.model.Mode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

private typealias FilesRoute = Route.Tabs.Files

fun NavGraphBuilder.tabFiles(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    searchQueryState: StateFlow<String>,
) = composable<FilesRoute> {
    val context = LocalContext.current
    var modeState by rememberSaveable { mutableStateOf<Mode>(Mode.Default) }

    UiStateHandler(modeState) {
        val mode = modeState
        val newUiState = when (mode) {
            Mode.Default -> FilesUiState
            is Mode.Multiselect -> FilesContextualUiState.copy(
                toolbar = FilesContextualUiState.toolbar.copy(
                    title = TextWrap.Text(
                        text = context.getString(R.string.toolbar_selected, mode.count)
                    )
                )
            )
        }
        onUiStateChange(newUiState)
    }

    val sheetCreateState = Compose.state()
    LaunchedEffect(Unit) {
        onFabClick.collectLatest {
            Haptic.rise()
            sheetCreateState.value = true
        }
    }

    FilesScreen(
        isStarred = false,
        searchQueryState = searchQueryState,
        onModeChange = { modeState = it },
        toExport = { id, exportUri ->

        },
        toDetails = {},
        sheetCreateState = sheetCreateState
    )
}

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
        title = TextWrap.Text(""),
        isContextual = true,
        actions = listOf(
            ToolbarActions.createFolder,
            ToolbarActions.star,
            ToolbarActions.delete,
        )
    )
)