package io.gromif.astracrypt.presentation.navigation.lab

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.wrappers.TextWrap

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab)
    )
)

internal fun NavGraphBuilder.labList() = composable<Route.LabGraph.List> {
    val navController = LocalNavController.current
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    LabScreen(
        navigateToAeadEncryption = { navController.navigate(Route.LabGraph.TinkGraph) },
        navigateToCombinedZip = { navController.navigate(Route.LabGraph.CombinedZip) },
    )
}
