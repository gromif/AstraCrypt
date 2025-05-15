package io.gromif.astracrypt

import android.view.Window
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nevidimka655.astracrypt.BuildConfig
import io.gromif.astracrypt.auth.presentation.AuthScreen
import io.gromif.astracrypt.auth.presentation.shared.onAuthType
import io.gromif.astracrypt.auth.presentation.shared.onSkinType
import io.gromif.astracrypt.presentation.navigation.MainNavHost
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.composables.BottomBarImpl
import io.gromif.astracrypt.presentation.navigation.composables.FloatingActionButtonImpl
import io.gromif.astracrypt.presentation.navigation.composables.appbar.SearchBarImpl
import io.gromif.astracrypt.presentation.navigation.composables.appbar.ToolbarImpl
import io.gromif.astracrypt.presentation.navigation.models.HostEvents
import io.gromif.astracrypt.presentation.navigation.models.HostStateHolder
import io.gromif.astracrypt.presentation.navigation.models.NavParams
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.ui.design_system.AstraCryptTheme
import io.gromif.astracrypt.utils.Api
import io.gromif.astracrypt.utils.SetSecureContentFlag
import io.gromif.astracrypt.utils.secureContent
import io.gromif.ui.compose.core.ext.FlowObserver
import io.gromif.ui.compose.core.ext.ObserveLifecycleEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@Suppress(
    "detekt:DestructuringDeclarationWithTooManyEntries",
    "detekt:LongMethod"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstraCryptApp(
    modifier: Modifier = Modifier,
    window: Window,
    vm: MainVM = viewModel<MainVM>(),
    navController: NavHostController = rememberNavController(),
    dynamicThemeState: State<Boolean> = vm.appearanceManager.dynamicThemeFlow.collectAsStateWithLifecycle(true),
) = AstraCryptTheme(
    isDynamicThemeSupported = Api.atLeast12(),
    dynamicThemeState = dynamicThemeState.value
) {
    vm.ObserveLifecycleEvents(LocalLifecycleOwner.current.lifecycle)
    val isWindowFocused = LocalWindowInfo.current.isWindowFocused
    val uiState = vm.uiState
    val (toolbar, fab, bottomBarTab, searchBar) = uiState.value
    var searchBarExpanded by rememberSaveable { mutableStateOf(false) }
    val onFabClick = remember { Channel<Any>(0) }
    val onToolbarActions = remember { Channel<ToolbarActions.Action>(0) }

    val secureContentMode by vm.secureContentStateFlow.collectAsStateWithLifecycle()
    SetSecureContentFlag(mode = secureContentMode, window = window)
    val authState by vm.authStateFlow.collectAsStateWithLifecycle(false)

    val topBarScroll = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val bottomBarScroll = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val toolbarIsCollapsing = remember(topBarScroll.state.collapsedFraction) {
        topBarScroll.state.collapsedFraction > 0f
    }
    val bottomBarIsCollapsing = remember(bottomBarScroll.state.collapsedFraction) {
        bottomBarScroll.state.collapsedFraction > 0f
    }
    val searchQueryState by vm.searchQueryState.collectAsStateWithLifecycle()
    val isSearching = remember(searchQueryState) { searchQueryState.isNotEmpty() }
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier
            .secureContent(mode = secureContentMode, windowHasFocus = isWindowFocused)
            .nestedScroll(
                if (searchBar) {
                    bottomBarScroll.nestedScrollConnection
                } else {
                    topBarScroll.nestedScrollConnection
                }
            ),
        topBar = {
            LaunchedEffect(searchBar) {
                if (!searchBar && !toolbar.isContextual) vm.setSearchQuery("")
            }
            if (searchBar) {
                SearchBarImpl(
                    query = searchQueryState,
                    onSearch = {
                        vm.setSearchQuery(query = it)
                        searchBarExpanded = false
                    },
                    onQueryChange = vm::setSearchQuery,
                    expanded = searchBarExpanded,
                    onExpandedChange = { searchBarExpanded = it },
                    backButtonEnabled = searchBarExpanded || isSearching
                )
            } else if (authState) {
                ToolbarImpl(
                    title = toolbar.title,
                    backButton = bottomBarTab == null,
                    isContextual = toolbar.isContextual,
                    actions = toolbar.actions,
                    onNavigateUp = navController::navigateUp,
                    onActionPressed = onToolbarActions::trySend,
                    scrollBehavior = topBarScroll
                )
            }
        },
        floatingActionButton = {
            FloatingActionButtonImpl(
                visible = (fab != null) &&
                    !toolbarIsCollapsing && !bottomBarIsCollapsing &&
                    !isSearching && !searchBarExpanded,
                imageVector = fab?.icon,
                contentDescription = fab?.contentDescription?.resolve(LocalContext.current)
            ) { onFabClick.trySend(0) }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            BottomBarImpl(
                visible = !isSearching && !searchBarExpanded && bottomBarTab != null,
                bottomBarScroll = bottomBarScroll,
                selected = bottomBarTab
            ) {
                navController.navigate(route = it.route) {
                    restoreState = true
                    launchSingleTop = true
                    popUpTo(Route.Tabs.Home) {
                        saveState = true
                    }
                }
            }
        }
    ) { padding ->
        if (!authState) {
            AuthContent(
                modifier = Modifier.fillMaxSize().padding(padding),
                onFabClickFlow = onFabClick.receiveAsFlow(),
                uiState = uiState
            )
        } else {
            MainNavHost(
                modifier = Modifier.padding(padding),
                navParams = NavParams(
                    isActionsSupported = Api.atLeast7(),
                    isDynamicColorsSupported = Api.atLeast12(),
                    applicationVersion = BuildConfig.VERSION_NAME
                ),
                hostStateHolder = HostStateHolder(
                    dynamicThemeState = dynamicThemeState.value,
                    snackbarHostState = snackbarHostState,
                    searchQueryState = vm.searchQueryState
                ),
                hostEvents = buildHostEvents(
                    onFabClickFlow = onFabClick.receiveAsFlow(),
                    onToolbarActionsFlow = onToolbarActions.receiveAsFlow(),
                    uiState = uiState
                ),
                navController = navController,
                onDynamicColorsStateChange = vm::setDynamicColorsState,
            )
        }
    }
}

@Composable
private fun AuthContent(
    modifier: Modifier = Modifier,
    onFabClickFlow: Flow<Any>,
    uiState: MutableState<UiState>,
) = AuthScreen(
    modifier = modifier,
    onFabClick = onFabClickFlow,
    onAuthType = object : onAuthType {
        override fun onPassword() {
            uiState.value = UiState(fab = UiState.Fab(icon = Icons.Default.Key))
        }
    },
    onSkinType = object : onSkinType {
        override fun onCalculator() {
            uiState.value = UiState()
        }
    }
)

private fun buildHostEvents(
    onFabClickFlow: Flow<Any>,
    onToolbarActionsFlow: Flow<ToolbarActions.Action>,
    uiState: MutableState<UiState>,
) = object : HostEvents {
    override fun setUiState(targetState: UiState) {
        uiState.value = targetState
    }

    @Composable
    override fun ObserveFab(action: suspend (Any) -> Unit) {
        FlowObserver(onFabClickFlow, action)
    }

    @Composable
    override fun ObserveToolbarActions(action: suspend (Any) -> Unit) {
        FlowObserver(onToolbarActionsFlow, action)
    }
}
