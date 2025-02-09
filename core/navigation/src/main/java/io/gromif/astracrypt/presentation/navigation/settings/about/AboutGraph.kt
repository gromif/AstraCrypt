package io.gromif.astracrypt.presentation.navigation.settings.about

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState

fun NavGraphBuilder.aboutGraph(
    onUiStateChange: (UiState) -> Unit,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    applicationVersion: String,
) = navigation<Route.AboutGraph>(startDestination = Route.AboutGraph.About) {
    about(
        onUiStateChange = onUiStateChange,
        snackbarHostState = snackbarHostState,
        toPrivacyPolicy = {
            navController.navigate(Route.AboutGraph.PrivacyPolicy)
        },
        applicationVersion = applicationVersion
    )
    privacyPolicy(onUiStateChange = onUiStateChange)
}