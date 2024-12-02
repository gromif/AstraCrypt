package com.nevidimka655.astracrypt.ui.navigation.composables.about

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.Route

inline fun NavGraphBuilder.aboutGraph(
    crossinline onUiStateChange: (UiState) -> Unit,
    navController: NavController
) = navigation<Route.AboutGraph>(startDestination = Route.AboutGraph.About) {
    about(onUiStateChange = onUiStateChange)
}