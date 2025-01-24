package com.nevidimka655.astracrypt.view.composables

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.domain.model.profile.ProfileInfo
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.composables.home.HomeViewModel
import com.nevidimka655.astracrypt.view.composables.settings.SettingsScreen
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.models.actions.lab
import com.nevidimka655.astracrypt.view.navigation.models.actions.notes
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.astracrypt.view.navigation.tabs.tabFiles
import com.nevidimka655.astracrypt.view.navigation.tabs.tabStarred
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.tabsGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>,
    searchQueryState: StateFlow<String>,
) {
    tabFiles(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        searchQueryState = searchQueryState
    )
    tabStarred(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        searchQueryState = searchQueryState,
        toFiles = { id, name ->
            navController.clearBackStack(Route.Tabs.Files())
            navController.navigate(Route.Tabs.Files(startParentId = id, startParentName = name)) {
                launchSingleTop = true
                popUpTo(Route.Tabs.Home) {
                    inclusive = false
                    saveState = true
                }
            }
        },
    )
    composable<Route.Tabs.Home> {
        UiStateHandler { onUiStateChange(HomeUiState) }
        val homeVm: HomeViewModel = hiltViewModel()
        val profileInfo by homeVm.profileInfoFlow.collectAsStateWithLifecycle(
            initialValue = ProfileInfo()
        )
        //val recentFiles by homeVm.recentFilesStateFlow.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) {
            onToolbarActions.collectLatest {
                when (it) {
                    ToolbarActions.notes -> navController.navigate(Route.NotesGraph)
                    ToolbarActions.lab -> navController.navigate(Route.LabGraph)
                }
            }
        }
        /*HomeScreen(
            recentFiles = recentFiles,
            imageLoader = homeVm.imageLoader,
            coilAvatarModel = homeVm.coilAvatarModel,
            defaultAvatar = profileInfo.defaultAvatar,
            name = profileInfo.name,
            onOpenRecent = {
                *//*if (it.isFile) navController.navigate(Route.Export(itemId = it.id)) else {
                    vm.openDirectory(
                        id = it.id,
                        dirName = it.name,
                        popBackStack = true
                    )
                    vm.triggerFilesListUpdate()
                    navController.navigate(BottomBarItems.Files.route)
                }*//*
            }
        )*/
    }
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

private val HomeUiState = UiState(
    toolbar = UiState.Toolbar(
        actions = listOf(ToolbarActions.notes, ToolbarActions.lab)
    ),
    bottomBarTab = BottomBarItems.Home
)

private val SettingsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings)
    ),
    bottomBarTab = BottomBarItems.Settings
)