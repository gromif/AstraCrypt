package com.nevidimka655.astracrypt.view.navigation.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.ui.compose_core.Compose
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.FilesScreen
import kotlinx.coroutines.flow.StateFlow

private typealias StarredRoute = Route.Tabs.Starred

fun NavGraphBuilder.tabStarred(
    onUiStateChange: (UiState) -> Unit,
    searchQueryState: StateFlow<String>,
) = composable<StarredRoute> {
    onUiStateChange(StarredUiState)

    FilesScreen(
        isStarred = true,
        searchQueryState = searchQueryState,
        toExport = { id, exportUri ->

        },
        toDetails = {},
        sheetCreateState = Compose.state()
    )
}

private val StarredUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.starred)
    ),
    bottomBarTab = BottomBarItems.Starred
)