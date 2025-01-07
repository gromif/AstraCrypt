package com.nevidimka655.astracrypt.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nevidimka655.astracrypt.auth.domain.AuthType
import com.nevidimka655.astracrypt.auth.domain.SkinType
import com.nevidimka655.astracrypt.auth.login.PasswordLoginScreen
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.composables.BottomBarImpl
import com.nevidimka655.astracrypt.view.navigation.composables.FloatingActionButtonImpl
import com.nevidimka655.astracrypt.view.navigation.composables.appbar.SearchBarImpl
import com.nevidimka655.astracrypt.view.navigation.composables.appbar.ToolbarImpl
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.root
import com.nevidimka655.atracrypt.core.design_system.AstraCryptTheme
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
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
    val barCollapsedFraction = topBarScroll.state.collapsedFraction
    val toolbarIsCollapsing = remember(barCollapsedFraction) { barCollapsedFraction > 0f }
    Scaffold(
        modifier = modifier.nestedScroll(topBarScroll.nestedScrollConnection),
        topBar = {
            LaunchedEffect(searchBar) {
                if (!searchBar) {
                    vm.isSearching = false
                    vm.searchQuery.value = null
                    vm.setSearchIsEnabled(false)
                }
            }
            if (searchBar) Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                BackHandler(enabled = vm.isSearching) {
                    vm.isSearching = false
                    vm.searchQuery.value = null
                    vm.setSearchIsEnabled(false)
                }
                SearchBarImpl(
                    modifier = Modifier.align(Alignment.TopCenter),
                    visible = !toolbarIsCollapsing,
                    query = vm.searchQuery.value ?: "",
                    onSearch = {
                        searchBarExpanded = false
                        vm.searchQuerySubmit(it)
                    },
                    onQueryChange = {
                        vm.searchQuery.value = it
                    },
                    expanded = searchBarExpanded,
                    onExpandedChange = {
                        vm.setSearchIsEnabled(it)
                        searchBarExpanded = it
                    },
                    backButtonEnabled = searchBarExpanded || vm.isSearching
                )
            } else if (vm.skinIsAuthenticated) ToolbarImpl(
                title = toolbar.title,
                backButton = vm.userIsAuthenticated && bottomBarTab == null,
                actions = toolbar.actions,
                onNavigateUp = {
                    Haptic.click()
                    navController.navigateUp()
                },
                onActionPressed = {
                    coroutineScope.launch { onToolbarActions.send(it) }
                },
                scrollBehavior = if (!vm.userIsAuthenticated) null else topBarScroll
            )
        },
        floatingActionButton = {
            val context = LocalContext.current
            FloatingActionButtonImpl(
                visible = !toolbarIsCollapsing && (fab != null) && !vm.isSearching && !searchBarExpanded,
                imageVector = fab?.icon,
                contentDescription = fab?.contentDescription?.resolve(context)
            ) { coroutineScope.launch { onFabClick.send(0) } }
        },
        bottomBar = {
            BottomBarImpl(
                visible = !toolbarIsCollapsing && !vm.isSearching && !searchBarExpanded && bottomBarTab != null,
                selected = bottomBarTab
            ) {
                navController.navigate(route = it.route) {
                    launchSingleTop = true
                    popUpTo(Route.Tabs.Home) { inclusive = false }
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
                        CalculatorScreen(modifier = Modifier.fillMaxSize()) { data ->
                            vm.verifySkin(data = data)
                        }
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
                vm = vm,
                navController = navController,
                onFabClick = onFabClick.receiveAsFlow(),
                onToolbarActions = onToolbarActions.receiveAsFlow()
            )
        )
    }
}