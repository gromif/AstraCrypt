package io.gromif.astracrypt.presentation.navigation.settings.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostStateHolder
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.about.AboutScreen
import io.gromif.ui.compose.core.wrappers.TextWrap

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings_about)
    )
)

internal fun NavGraphBuilder.about(
    applicationVersion: String,
) = composable<Route.AboutGraph.About> {
    val navController = LocalNavController.current
    val hostStateHolder = LocalHostStateHolder.current
    val hostEvents = LocalHostEvents.current

    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    AboutScreen(
        modifier = Modifier.fillMaxSize(),
        snackbarHostState = hostStateHolder.snackbarHostState,
        version = applicationVersion,
        toPrivacyPolicy = {
            navController.navigate(Route.AboutGraph.PrivacyPolicy)
        }
    )
}
