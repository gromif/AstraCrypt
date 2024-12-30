package com.nevidimka655.astracrypt.view.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
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
import com.nevidimka655.astracrypt.domain.model.profile.ProfileInfo
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.MainVM
import com.nevidimka655.astracrypt.view.composables.files.FilesScreen
import com.nevidimka655.astracrypt.view.composables.files.FilesViewModel
import com.nevidimka655.astracrypt.view.composables.home.HomeScreen
import com.nevidimka655.astracrypt.view.composables.home.HomeViewModel
import com.nevidimka655.astracrypt.view.composables.settings.SettingsScreen
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.models.actions.lab
import com.nevidimka655.astracrypt.view.models.actions.notes
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.tabs(
    onUiStateChange: (UiState) -> Unit,
    vm: MainVM,
    navController: NavController,
    onFabClick: Flow<Any>,
    onToolbarActions: Flow<ToolbarActions.Action>
) {
    composable<Route.Tabs.Home> {
        onUiStateChange(HomeUiState)
        val homeVm: HomeViewModel = hiltViewModel()
        val profileInfo by homeVm.profileInfoFlow.collectAsStateWithLifecycle(
            initialValue = ProfileInfo()
        )
        val recentFiles by homeVm.recentFilesStateFlow.collectAsStateWithLifecycle()
        LaunchedEffect(Unit) {
            onToolbarActions.collectLatest {
                when (it) {
                    ToolbarActions.notes -> navController.navigate(Route.NotesGraph)
                    ToolbarActions.lab -> navController.navigate(Route.LabGraph)
                }
            }
        }
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
            if (files.isStarred) StarredUiState else FilesUiState
        )
        vm.isStarredScreen = files.isStarred

        val filesVm: FilesViewModel = hiltViewModel()
        val viewMode by vm.filesViewModeState.collectAsStateWithLifecycle()
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
        onUiStateChange(SettingsUiState)
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

private val FilesUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files)
    ),
    fab = UiState.Fab(icon = Icons.Default.Add),
    bottomBarTab = BottomBarItems.Files,
    searchBar = true
)

private val StarredUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.starred)
    ),
    bottomBarTab = BottomBarItems.Starred
)

private val SettingsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings)
    ),
    bottomBarTab = BottomBarItems.Settings
)