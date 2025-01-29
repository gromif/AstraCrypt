package com.nevidimka655.astracrypt.view.navigation.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.models.actions.lab
import com.nevidimka655.astracrypt.view.navigation.models.actions.notes
import com.nevidimka655.astracrypt.view.navigation.shared.ToolbarActionsObserver
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.shared.recent.list.Actions
import io.gromif.astracrypt.home.presentation.HomeScreen
import kotlinx.coroutines.flow.Flow

private typealias HomeRoute = Route.Tabs.Home

private val HomeUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.home),
        actions = listOf(ToolbarActions.notes, ToolbarActions.lab)
    ),
    bottomBarTab = BottomBarItems.Home
)

internal fun NavGraphBuilder.tabHome(
    onUiStateChange: (UiState) -> Unit,
    onToolbarActions: Flow<ToolbarActions.Action>,
    navigateToNotes: () -> Unit,
    navigateToLab: () -> Unit,
    actions: Actions,
) = composable<HomeRoute> {
    UiStateHandler { onUiStateChange(HomeUiState) }

    ToolbarActionsObserver(onToolbarActions) {
        when (it) {
            ToolbarActions.notes -> navigateToNotes()
            ToolbarActions.lab -> navigateToLab()
        }
    }

    HomeScreen(recentFilesActions = actions)
}