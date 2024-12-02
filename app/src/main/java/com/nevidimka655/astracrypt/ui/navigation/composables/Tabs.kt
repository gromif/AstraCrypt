package com.nevidimka655.astracrypt.ui.navigation.composables

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.features.details.DetailsScreen
import com.nevidimka655.astracrypt.features.details.DetailsScreenViewModel
import com.nevidimka655.astracrypt.features.profile.ProfileInfo
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.BottomBarItems
import com.nevidimka655.astracrypt.ui.navigation.Route
import com.nevidimka655.astracrypt.ui.tabs.files.FilesScreen
import com.nevidimka655.astracrypt.ui.tabs.files.FilesViewModel
import com.nevidimka655.astracrypt.ui.tabs.home.HomeScreen
import com.nevidimka655.astracrypt.ui.tabs.home.HomeViewModel
import com.nevidimka655.astracrypt.ui.tabs.settings.SettingsScreen
import com.nevidimka655.astracrypt.utils.enums.ViewMode
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
        HomeScreen(
            recentItemsState = homeVm.recentFilesStateFlow.collectAsStateWithLifecycle(),
            imageLoader = homeVm.imageLoader,
            coilAvatarModel = homeVm.coilAvatarModel,
            defaultAvatar = profileInfo.defaultAvatar,
            name = profileInfo.name,
            onOpenRecent = {
                if (it.isFile) navController.navigate(Route.Tabs.Export(itemId = it.id)) else {
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
        FilesScreen(
            vm = vm,
            filesVM = filesVm,
            viewMode = viewMode,
            isStarred = files.isStarred,
            dialogNewFolderState = dialogNewFolderState,
            onFabClick = onFabClick,
            onNavigateUp = { navController.navigateUp() },
            onNavigateToDetails = { navController.navigate(Route.Tabs.Details(itemId = it)) },
            onOpenStarredDir = { navController.navigate(BottomBarItems.Files.route) },
            onOpenFile = { navController.navigate(Route.Tabs.Export(itemId = it)) },
            onExport = { itemId, outUri ->
                navController.navigate(
                    Route.Tabs.Export(itemId = itemId, outUri = outUri.toString())
                )
            },
            onRename = { itemId, newName ->
                filesVm.rename(vm.encryptionInfo, itemId, newName)
            },
            onNavigatorClick = { vm.openDirectoryFromSelector(it) }
        ) { }
    }
    composable<Route.Tabs.Settings> {
        onUiStateChange(Route.Tabs.Settings.Ui.state)
        SettingsScreen()
    }
    composable<Route.Tabs.Details> {
        val details: Route.Tabs.Details = it.toRoute()
        onUiStateChange(Route.Tabs.Details.Ui.state)
        val context = LocalContext.current
        val vm1: DetailsScreenViewModel = hiltViewModel()
        DetailsScreen(
            detailsManager = vm1.detailsManager,
            imageLoader = vm1.imageLoader,
            onStart = {
                vm1.submitDetailsQuery(context = context, itemId = details.itemId)
            }
        )
    }
}