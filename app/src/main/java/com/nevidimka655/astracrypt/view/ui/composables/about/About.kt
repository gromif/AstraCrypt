package com.nevidimka655.astracrypt.view.ui.composables.about

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.tabs.settings.about.AboutScreen
import com.nevidimka655.astracrypt.view.ui.navigation.Route

inline fun NavGraphBuilder.about(
    crossinline onUiStateChange: (UiState) -> Unit,
    noinline navigateToPrivacyPolicy: () -> Unit
) = composable<Route.AboutGraph.About> {
    onUiStateChange(Route.AboutGraph.About.Ui.state)
    AboutScreen(
        navigateToPrivacyPolicy = navigateToPrivacyPolicy
    )
}