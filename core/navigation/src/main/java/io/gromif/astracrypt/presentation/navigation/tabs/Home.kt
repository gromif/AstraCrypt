package io.gromif.astracrypt.presentation.navigation.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.recent.list.Actions
import io.gromif.astracrypt.home.presentation.HomeScreen
import io.gromif.astracrypt.presentation.navigation.BottomBarItems
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.lab
import io.gromif.astracrypt.presentation.navigation.models.actions.notes
import io.gromif.astracrypt.presentation.navigation.shared.ToolbarActionsObserver
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
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