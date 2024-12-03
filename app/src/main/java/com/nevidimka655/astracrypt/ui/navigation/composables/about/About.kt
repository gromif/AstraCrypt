package com.nevidimka655.astracrypt.ui.navigation.composables.about

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.Route
import com.nevidimka655.astracrypt.ui.tabs.settings.about.AboutScreen

inline fun NavGraphBuilder.about(
    crossinline onUiStateChange: (UiState) -> Unit,
    noinline navigateToPrivacyPolicy: () -> Unit
) = composable<Route.AboutGraph.About> {
    onUiStateChange(Route.AboutGraph.About.Ui.state)
    AboutScreen(
        navigateToPrivacyPolicy = navigateToPrivacyPolicy
    )
}