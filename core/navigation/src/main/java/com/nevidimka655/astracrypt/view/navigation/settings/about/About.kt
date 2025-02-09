package com.nevidimka655.astracrypt.view.navigation.settings.about

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.settings.about.AboutScreen

private val AboutUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_about)
    )
)

fun NavGraphBuilder.about(
    onUiStateChange: (UiState) -> Unit,
    snackbarHostState: SnackbarHostState,
    toPrivacyPolicy: () -> Unit,
    applicationVersion: String,
) = composable<Route.AboutGraph.About> {
    UiStateHandler { onUiStateChange(AboutUiState) }
    AboutScreen(
        snackbarHostState = snackbarHostState,
        version = applicationVersion,
        toPrivacyPolicy = toPrivacyPolicy
    )
}