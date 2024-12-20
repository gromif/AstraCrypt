package com.nevidimka655.astracrypt.view.composables.settings.about.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route

fun NavGraphBuilder.aboutGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController
) = navigation<Route.AboutGraph>(startDestination = Route.AboutGraph.About) {
    about(
        onUiStateChange = onUiStateChange,
        navigateToPrivacyPolicy = {
            navController.navigate(Route.AboutGraph.PrivacyPolicy)
        })
    privacyPolicy(onUiStateChange = onUiStateChange)
}