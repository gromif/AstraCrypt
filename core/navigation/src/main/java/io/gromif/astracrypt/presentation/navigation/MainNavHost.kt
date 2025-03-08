package io.gromif.astracrypt.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import io.gromif.astracrypt.presentation.navigation.models.HostEvents
import io.gromif.astracrypt.presentation.navigation.models.HostStateHolder
import io.gromif.astracrypt.presentation.navigation.models.NavParams
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostStateHolder
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navParams: NavParams,
    hostStateHolder: HostStateHolder,
    hostEvents: HostEvents,
    navController: NavHostController,
    onDynamicColorsStateChange: (Boolean) -> Unit,
) = CompositionLocalProvider(
    LocalNavController provides navController,
    LocalHostStateHolder provides hostStateHolder,
    LocalHostEvents provides hostEvents
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarItems.Home.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        modifier = modifier,
        builder = root(
            navParams = navParams,
            onDynamicColorsStateChange = onDynamicColorsStateChange,
        )
    )
}