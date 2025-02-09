package io.gromif.astracrypt.presentation.navigation.settings.about

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
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