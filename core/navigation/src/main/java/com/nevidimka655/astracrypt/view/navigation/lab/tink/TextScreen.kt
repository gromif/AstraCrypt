package com.nevidimka655.astracrypt.view.navigation.lab.tink

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.tink_lab.TinkLab
import com.nevidimka655.tink_lab.text.TextScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(title = TextWrap.Resource(id = R.string.text))
)

internal fun NavGraphBuilder.tinkText(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.LabGraph.TinkGraph.Text> {
    UiStateHandler { onUiStateChange(ScreenUiState) }
    val route: Route.LabGraph.TinkGraph.Text = it.toRoute()

    TinkLab.TextScreen(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        rawKeyset = route.rawKeyset
    )
}