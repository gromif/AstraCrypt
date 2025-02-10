package com.nevidimka655.astracrypt.view

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.root
import com.nevidimka655.atracrypt.core.design_system.AstraCryptTheme
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.presentation.PasswordLoginScreen
import io.gromif.astracrypt.presentation.navigation.BottomBarItems
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.composables.BottomBarImpl
import io.gromif.astracrypt.presentation.navigation.composables.FloatingActionButtonImpl
import io.gromif.astracrypt.presentation.navigation.composables.appbar.SearchBarImpl
import io.gromif.astracrypt.presentation.navigation.composables.appbar.ToolbarImpl
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.utils.Api
import io.gromif.calculator.CalculatorScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstraCryptApp(
    modifier: Modifier = Modifier,
    vm: MainVM = viewModel<MainVM>(),
    navController: NavHostController = rememberNavController(),
) = AstraCryptTheme(
    isDynamicThemeSupported = Api.atLeast12(),
    dynamicThemeFlow = vm.appearanceManager.dynamicThemeFlow
) {
    var uiState by vm.uiState
    val (toolbar, fab, bottomBarTab, searchBar) = uiState
    var searchBarExpanded by rememberSaveable { mutableStateOf(false) }
    val onFabClick = remember { Channel<Any>(0) }
    val onToolbarActions = remember { Channel<ToolbarActions.Action>(0) }

    val coroutineScope = rememberCoroutineScope()
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
        modifier = modifier.nestedScroll(
            if (searchBar) bottomBarScroll.nestedScrollConnection else {
                topBarScroll.nestedScrollConnection
            }
        ),
        topBar = {
            LaunchedEffect(searchBar) {
                if (!searchBar && !toolbar.isContextual) vm.setSearchQuery("")
            }
            if (searchBar) SearchBarImpl(
                query = searchQueryState,
                onSearch = {
                    vm.setSearchQuery(query = it)
                    searchBarExpanded = false
                },
                onQueryChange = { vm.setSearchQuery(query = it) },
                expanded = searchBarExpanded,
                onExpandedChange = { searchBarExpanded = it },
                backButtonEnabled = searchBarExpanded || isSearching
            ) else if (vm.skinIsAuthenticated) ToolbarImpl(
                title = toolbar.title,
                backButton = vm.userIsAuthenticated && bottomBarTab == null,
                isContextual = toolbar.isContextual,
                actions = toolbar.actions,
                onNavigateUp = navController::navigateUp,
                onActionPressed = {
                    coroutineScope.launch { onToolbarActions.send(it) }
                },
                scrollBehavior = if (!vm.userIsAuthenticated) null else topBarScroll
            )
        },
        floatingActionButton = {
            FloatingActionButtonImpl(
                visible = (fab != null) &&
                        !toolbarIsCollapsing && !bottomBarIsCollapsing &&
                        !isSearching && !searchBarExpanded,
                imageVector = fab?.icon,
                contentDescription = fab?.contentDescription?.resolve(LocalContext.current)
            ) { coroutineScope.launch { onFabClick.send(0) } }
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
                        inclusive = false
                        saveState = true
                    }
                }
            }
        }
    ) { padding ->
        if (!vm.userIsAuthenticated) {
            val auth by vm.authState.collectAsState()
            auth?.let {
                if (!vm.skinIsAuthenticated) when (it.skinType) {
                    SkinType.Calculator -> {
                        uiState = UiState(
                            toolbar = UiState.Toolbar(title = TextWrap.Resource(id = R.string.settings_camouflageType_calculator))
                        )
                        CalculatorScreen(
                            modifier = Modifier.fillMaxSize(),
                            onCalculate = vm::verifySkin
                        )
                        return@let
                    }

                    null -> vm.skinIsAuthenticated = true
                }
                when (it.type) {
                    AuthType.PASSWORD -> {
                        uiState = UiState(
                            toolbar = UiState.Toolbar(title = TextWrap.Resource(id = R.string.settings_authentication)),
                            fab = UiState.Fab(icon = Icons.Default.Key)
                        )
                        PasswordLoginScreen(
                            modifier = Modifier.fillMaxSize(),
                            auth = it,
                            onFabClick = onFabClick.receiveAsFlow(),
                            onAuthenticated = { vm.userIsAuthenticated = true }
                        )
                    }

                    null -> vm.userIsAuthenticated = true
                }
            }
        } else NavHost(
            navController,
            startDestination = BottomBarItems.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) },
            modifier = Modifier.padding(padding),
            builder = root(
                onUiStateChange = { uiState = it },
                navController = navController,
                onFabClick = onFabClick.receiveAsFlow(),
                onToolbarActions = onToolbarActions.receiveAsFlow(),
                snackbarHostState = snackbarHostState,
                searchQueryState = vm.searchQueryState
            )
        )
    }
}