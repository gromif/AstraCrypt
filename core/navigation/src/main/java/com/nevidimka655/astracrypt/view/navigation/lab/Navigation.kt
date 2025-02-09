package com.nevidimka655.astracrypt.view.navigation.lab

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab)
    )
)

internal fun NavGraphBuilder.labList(
    onUiStateChange: (UiState) -> Unit,
    navigateToAeadEncryption: () -> Unit = {},
    navigateToCombinedZip: () -> Unit = {}
) = composable<Route.LabGraph.List> {
    UiStateHandler { onUiStateChange(ScreenUiState) }

    LabScreen(
        navigateToAeadEncryption = navigateToAeadEncryption,
        navigateToCombinedZip = navigateToCombinedZip
    )
}