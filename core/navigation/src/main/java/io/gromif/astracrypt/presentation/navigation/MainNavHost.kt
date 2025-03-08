package io.gromif.astracrypt.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    onUiStateChange: (UiState) -> Unit,
    isActionsSupported: Boolean,
    navController: NavHostController,
    onFabClick: Flow<Any>,
    onToolbarActions: Flow<ToolbarActions.Action>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>,
    dynamicThemeState: Boolean,
    isDynamicColorsSupported: Boolean,
    onDynamicColorsStateChange: (Boolean) -> Unit,
    applicationVersion: String,
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarItems.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        modifier = modifier,
        builder = root(
            onUiStateChange = onUiStateChange,
            isActionsSupported = isActionsSupported,
            navController = navController,
            onFabClick = onFabClick,
            onToolbarActions = onToolbarActions,
            snackbarHostState = snackbarHostState,
            searchQueryState = searchQueryState,
            dynamicThemeState = dynamicThemeState,
            isDynamicColorsSupported = isDynamicColorsSupported,
            onDynamicColorsStateChange = onDynamicColorsStateChange,
            applicationVersion = applicationVersion
        )
    )
}