package com.nevidimka655.astracrypt.view.navigation.tabs

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.astracrypt.view.navigation.tabs.settings.SettingsScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.model.action.FilesNavActions
import io.gromif.astracrypt.files.shared.recent.list.Actions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

fun NavGraphBuilder.tabsGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>,
) {

    fun openFolderInFiles(id: Long, name: String) = with(navController) {
        clearBackStack(Route.Tabs.Files())
        navigate(Route.Tabs.Files(startParentId = id, startParentName = name)) {
            launchSingleTop = true
            popUpTo(Route.Tabs.Home) { saveState = true }
        }
    }

    val filesNavActions = object : FilesNavActions {
        override fun toFiles(id: Long, name: String) {
            openFolderInFiles(id, name)
        }

        override fun toExport(id: Long, output: Uri) {
            navController.navigate(Route.Export(
                isExternalExport = true,
                itemId = id,
                outUri = output.toString()
            ))
        }

        override fun toExportPrivately(id: Long) {
            navController.navigate(Route.Export(
                isExternalExport = false,
                itemId = id
            ))
        }

        override fun toDetails(id: Long) {
            navController.navigate(Route.Details(id = id))
        }
    }

    tabFiles(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        snackbarHostState = snackbarHostState,
        searchQueryState = searchQueryState,
        navActions = filesNavActions,
    )
    tabStarred(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        snackbarHostState = snackbarHostState,
        searchQueryState = searchQueryState,
        navActions = filesNavActions,
    )
    tabHome(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        navigateToNotes = { navController.navigate(Route.NotesGraph) },
        navigateToLab = { navController.navigate(Route.LabGraph) },
        actions = object : Actions {
            override fun openFile(id: Long) {
                navController.navigate(Route.Export(isExternalExport = false, itemId = id))
            }

            override fun openFolder(id: Long, name: String) {
                openFolderInFiles(id, name)
            }
        }
    )
    composable<Route.Tabs.Settings> {
        UiStateHandler { onUiStateChange(SettingsUiState) }
        SettingsScreen(
            navigateToEditProfile = { navController.navigate(Route.EditProfile) },
            navigateToUi = { navController.navigate(Route.SettingsUi) },
            navigateToSecurity = { navController.navigate(Route.SettingsSecurity) },
            navigateToAbout = { navController.navigate(Route.AboutGraph) }
        )
    }
}

private val SettingsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings)
    ),
    bottomBarTab = BottomBarItems.Settings
)