package com.nevidimka655.astracrypt.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.details.DetailsScreen
import com.nevidimka655.astracrypt.features.details.DetailsScreenViewModel
import com.nevidimka655.astracrypt.features.export.ExportScreen
import com.nevidimka655.astracrypt.features.export.ExportScreenViewModel
import com.nevidimka655.astracrypt.features.profile.ProfileInfo
import com.nevidimka655.astracrypt.tabs.settings.SettingsScreen
import com.nevidimka655.astracrypt.ui.navigation.BottomBarItems
import com.nevidimka655.astracrypt.ui.navigation.Route
import com.nevidimka655.astracrypt.ui.tabs.files.FilesScreen
import com.nevidimka655.astracrypt.ui.tabs.files.FilesViewModel
import com.nevidimka655.astracrypt.ui.tabs.home.HomeScreen
import com.nevidimka655.astracrypt.ui.tabs.home.HomeViewModel
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.enums.ViewMode
import com.nevidimka655.haptic.Haptic
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    modifier: Modifier = Modifier,
    vm: MainVM = viewModel<MainVM>(),
    navController: NavHostController = rememberNavController()
) = AstraCryptTheme(
    dynamicThemeFlow = vm.appearanceManager.dynamicThemeFlow
) {
    Surface {
        val context = LocalContext.current
        var uiState by remember { mutableStateOf(UiState()) }
        val (toolbar, fab, bottomBarTab) = uiState
        val onFabClick = remember { Channel<Any>(0) }

        val coroutineScope = rememberCoroutineScope()
        val topBarScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
        var fabAnimatedVisibilityState by rememberSaveable(fab) { mutableStateOf(fab.visible) }
        if (fab.visible) LaunchedEffect(topBarScroll.state.collapsedFraction) {
            val toolbarIsCollapsing = topBarScroll.state.collapsedFraction > 0f
            fabAnimatedVisibilityState = !toolbarIsCollapsing
        }
        LaunchedEffect(Unit) {
            with(vm) {
                if (!isDatabaseCreated()) setupForFirstUse()
            }
        }
        Scaffold(
            modifier = modifier.nestedScroll(topBarScroll.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = toolbar.title.resolve(context))
                    },
                    navigationIcon = {
                        if (bottomBarTab == null) IconButton(onClick = {
                            Toast.makeText(context, "${bottomBarTab}", Toast.LENGTH_SHORT).show()
                            Haptic.click()
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = context.getString(R.string.back)
                            )
                        }
                    },
                    scrollBehavior = topBarScroll,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = fabAnimatedVisibilityState,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    LargeFloatingActionButton(
                        onClick = {
                            coroutineScope.launch { onFabClick.send(0) }
                        },
                    ) { Icon(fab.icon.imageVector, fab.contentDescription.resolve(context)) }
                }
            },
            bottomBar = {
                val localDensity = LocalDensity.current
                val windowInsets = WindowInsets.systemBars
                AnimatedVisibility(
                    visible = bottomBarTab != null,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically(targetHeight = {
                        windowInsets.getBottom(localDensity)
                    }) + fadeOut()
                ) {
                    BottomAppBar {
                        BottomBarItems.entries.forEach {
                            NavigationBarItem(
                                selected = bottomBarTab == it,
                                onClick = {
                                    if (bottomBarTab != it) navController.navigate(
                                        route = it.route,
                                        navOptions = NavOptions.Builder()
                                            .setLaunchSingleTop(true)
                                            .setPopUpTo<Route.Tabs.Home>(false)
                                            .build()
                                    )
                                },
                                icon = {
                                    Icon(
                                        if (bottomBarTab == it) it.icon else it.iconOutline,
                                        context.getString(it.titleId)
                                    )
                                },
                                label = {
                                    Text(
                                        text = context.getString(it.titleId),
                                        fontWeight = if (bottomBarTab == it) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController,
                startDestination = BottomBarItems.Home.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) },
                modifier = Modifier.padding(padding)
            ) {
                composable<Route.Tabs.Home> {
                    uiState = Route.Tabs.Home.Ui.state
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
                            if (it.isFile) export(navController, it.id) else {
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
                    uiState =
                        if (files.isStarred) Route.Tabs.Files.Ui.starred else Route.Tabs.Files.Ui.files
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
                        onOpenFile = { export(navController, it) },
                        onExport = { itemId, outUri ->
                            export(navController, itemId, outUri.toString())
                        },
                        onRename = { itemId, newName ->
                            filesVm.rename(vm.encryptionInfo, itemId, newName)
                        },
                        onNavigatorClick = { vm.openDirectoryFromSelector(it) }
                    ) { }
                }
                composable<Route.Tabs.Details> {
                    val details: Route.Tabs.Details = it.toRoute()
                    uiState = Route.Tabs.Details.Ui.state
                    val context = LocalContext.current
                    val vm1: DetailsScreenViewModel = hiltViewModel()
                    DetailsScreen(
                        detailsManager = vm1.detailsManager,
                        imageLoader = vm1.imageLoader,
                        onStart = {
                            vm1.submitDetailsQuery(context, vm.encryptionInfo, details.itemId)
                        }
                    )
                }
                composable<Route.Tabs.Export> {
                    val export: Route.Tabs.Export = it.toRoute()
                    val vm1: ExportScreenViewModel = hiltViewModel()
                    ExportScreen(
                        state = vm1.uiState,
                        isExternalExport = export.outUri != null,
                        onStart = {
                            if (export.outUri != null) vm1.export(
                                encryptionInfo = vm.encryptionInfo,
                                itemId = export.itemId,
                                output = export.outUri
                            ).invokeOnCompletion { vm1.observeWorkInfoState() } else vm1.export(
                                encryptionInfo = vm.encryptionInfo,
                                itemId = export.itemId
                            )
                        },
                        onOpenExportedFile = { vm1.openExportedFile(context = context) },
                        onCancelExport = { vm1.cancelExport() },
                        onDispose = { vm1.onDispose() }
                    )
                }
                composable<Route.Tabs.Settings> {
                    uiState = Route.Tabs.Settings.Ui.state
                    SettingsScreen()
                }
            }
        }
    }
}

private fun export(navController: NavController, itemId: Long, outUri: String? = null) =
    navController.navigate(Route.Tabs.Export(itemId = itemId, outUri = outUri))