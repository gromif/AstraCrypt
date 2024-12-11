package com.nevidimka655.astracrypt.view.ui.composables.about

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route

inline fun NavGraphBuilder.aboutGraph(
    crossinline onUiStateChange: (UiState) -> Unit,
    navController: NavController
) = navigation<Route.AboutGraph>(startDestination = Route.AboutGraph.About) {
    about(
        onUiStateChange = onUiStateChange,
        navigateToPrivacyPolicy = {
            navController.navigate(Route.AboutGraph.PrivacyPolicy)
        })
    privacyPolicy(onUiStateChange = onUiStateChange)
}