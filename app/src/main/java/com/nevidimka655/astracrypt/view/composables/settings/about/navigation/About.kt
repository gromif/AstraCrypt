package com.nevidimka655.astracrypt.view.composables.settings.about.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.composables.settings.about.AboutScreen
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val AboutUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_about)
    )
)

fun NavGraphBuilder.about(
    onUiStateChange: (UiState) -> Unit,
    navigateToPrivacyPolicy: () -> Unit
) = composable<Route.AboutGraph.About> {
    onUiStateChange(AboutUiState)
    AboutScreen(
        navigateToPrivacyPolicy = navigateToPrivacyPolicy
    )
}