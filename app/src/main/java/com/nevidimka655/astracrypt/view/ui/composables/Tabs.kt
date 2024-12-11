package com.nevidimka655.astracrypt.view.ui.composables

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.nevidimka655.astracrypt.features.profile.ProfileInfo
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ViewMode
import com.nevidimka655.astracrypt.view.ui.MainVM
import com.nevidimka655.astracrypt.view.ui.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.files.FilesScreen
import com.nevidimka655.astracrypt.view.ui.tabs.files.FilesViewModel
import com.nevidimka655.astracrypt.view.ui.tabs.home.HomeScreen
import com.nevidimka655.astracrypt.view.ui.tabs.home.HomeViewModel
import com.nevidimka655.astracrypt.view.ui.tabs.settings.SettingsScreen
import kotlinx.coroutines.channels.Channel

inline fun NavGraphBuilder.tabs(
    crossinline onUiStateChange: (UiState) -> Unit,
    vm: MainVM,
    navController: NavController,
    onFabClick: Channel<Any>
) {
    composable<Route.Tabs.Home> {
        onUiStateChange(Route.Tabs.Home.Ui.state)
        val homeVm: HomeViewModel = hiltViewModel()
        val profileInfo by homeVm.profileInfoFlow.collectAsStateWithLifecycle(
            initialValue = ProfileInfo()
        )
        val recentFiles by homeVm.recentFilesStateFlow.collectAsStateWithLifecycle()
        HomeScreen(
            recentFiles = recentFiles,
            imageLoader = homeVm.imageLoader,
            coilAvatarModel = homeVm.coilAvatarModel,
            defaultAvatar = profileInfo.defaultAvatar,
            name = profileInfo.name,
            onOpenRecent = {
                if (it.isFile) navController.navigate(Route.Export(itemId = it.id)) else {
                    vm.openDirectory(
                        id = it.id,
                        dirName = it.name,
                        popBackStack = true
                    )
                    vm.triggerFilesListUpdate()
                    navController.navigate(BottomBarItems.Files.route)
                }
            }
        )
    }
    composable<Route.Tabs.Files> {
        val files: Route.Tabs.Files = it.toRoute()
        onUiStateChange(
            if (files.isStarred) Route.Tabs.Files.Ui.starred else Route.Tabs.Files.Ui.files
        )
        vm.isStarredScreen = files.isStarred

        val filesVm: FilesViewModel = hiltViewModel()
        val viewMode by filesVm.appearanceManager.filesViewModeFlow.collectAsStateWithLifecycle(
            initialValue = ViewMode.Grid
        )
        val dialogNewFolderState = rememberSaveable { mutableStateOf(false) }
        val items = (if (files.isStarred) vm.starredPagingFlow else vm.pagingFlow)
            .collectAsLazyPagingItems()
        FilesScreen(
            vm = vm,
            filesVM = filesVm,
            viewMode = viewMode,
            items = items,
            isStarred = files.isStarred,
            dialogNewFolderState = dialogNewFolderState,
            onFabClick = onFabClick,
            onNavigateUp = { navController.navigateUp() },
            onNavigateToDetails = { navController.navigate(Route.Details(itemId = it)) },
            onOpenStarredDir = { navController.navigate(BottomBarItems.Files.route) },
            onOpenFile = { navController.navigate(Route.Export(itemId = it)) },
            onNewFolder = { vm.newDirectory(it) },
            onExport = { itemId, outUri ->
                navController.navigate(
                    Route.Export(itemId = itemId, outUri = outUri.toString())
                )
            },
            onRename = { itemId, newName ->
                filesVm.rename(itemId, newName)
            },
            onNavigatorClick = { vm.openDirectoryFromSelector(it) }
        ) { }
    }
    composable<Route.Tabs.Settings> {
        onUiStateChange(Route.Tabs.Settings.Ui.state)
        SettingsScreen(
            navigateToEditProfile = { navController.navigate(Route.EditProfile) },
            navigateToUi = { navController.navigate(Route.SettingsUi) },
            navigateToSecurity = { navController.navigate(Route.SettingsSecurity) },
            navigateToAbout = { navController.navigate(Route.AboutGraph) }
        )
    }
}