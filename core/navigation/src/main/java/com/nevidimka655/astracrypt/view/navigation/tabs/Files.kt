package com.nevidimka655.astracrypt.view.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.FilesScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

private typealias FilesRoute = Route.Tabs.Files

fun NavGraphBuilder.tabFiles(
    onUiStateChange: (UiState) -> Unit,
    onFabClick: Flow<Any>,
    searchQueryState: StateFlow<String>,
) = composable<FilesRoute> {
    onUiStateChange(FilesUiState)
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