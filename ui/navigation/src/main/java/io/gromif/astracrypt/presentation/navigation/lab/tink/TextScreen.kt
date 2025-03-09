package io.gromif.astracrypt.presentation.navigation.lab.tink

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.tink_lab.presentation.TinkLab
import io.gromif.tink_lab.presentation.text.TextScreen
import io.gromif.ui.compose.core.wrappers.TextWrap

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(title = TextWrap.Resource(id = R.string.text))
)

internal fun NavGraphBuilder.tinkText() = composable<Route.LabGraph.TinkGraph.Text> {
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    val route: Route.LabGraph.TinkGraph.Text = it.toRoute()

    TinkLab.TextScreen(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        rawKeyset = route.rawKeyset
    )
}