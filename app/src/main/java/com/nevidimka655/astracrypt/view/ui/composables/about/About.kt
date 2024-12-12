package com.nevidimka655.astracrypt.view.ui.composables.about

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.astracrypt.view.ui.tabs.settings.about.AboutScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val AboutUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_about)
    )
)

inline fun NavGraphBuilder.about(
    crossinline onUiStateChange: (UiState) -> Unit,
    noinline navigateToPrivacyPolicy: () -> Unit
) = composable<Route.AboutGraph.About> {
    onUiStateChange(AboutUiState)
    AboutScreen(
        navigateToPrivacyPolicy = navigateToPrivacyPolicy
    )
}