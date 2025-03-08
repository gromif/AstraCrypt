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
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

private typealias HomeRoute = Route.Tabs.Home

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.home),
        actions = listOf(ToolbarActions.notes, ToolbarActions.lab)
    ),
    bottomBarTab = BottomBarItems.Home
)

internal fun NavGraphBuilder.tabHome() = composable<HomeRoute> {
    val navController = LocalNavController.current
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    hostEvents.ObserveToolbarActions {
        when (it) {
            ToolbarActions.notes -> navController.navigate(Route.NotesGraph)
            ToolbarActions.lab -> navController.navigate(Route.LabGraph)
        }
    }

    HomeScreen(
        recentFilesActions = object : Actions {
            override fun openFile(id: Long) {
                navController.navigate(Route.Export(isExternalExport = false, itemId = id))
            }

            override fun openFolder(id: Long, name: String) = with(navController) {
                clearBackStack(Route.Tabs.Files())
                navigate(Route.Tabs.Files(startParentId = id, startParentName = name)) {
                    launchSingleTop = true
                    popUpTo(Route.Tabs.Home) { saveState = true }
                }
            }
        }
    )
}