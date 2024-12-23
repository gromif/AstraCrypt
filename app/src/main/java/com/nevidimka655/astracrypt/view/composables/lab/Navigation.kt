package com.nevidimka655.astracrypt.view.composables.lab

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val LabListUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab)
    )
)

fun NavGraphBuilder.labList(
    onUiStateChange: (UiState) -> Unit,
    navigateToAeadEncryption: () -> Unit = {},
    navigateToCombinedZip: () -> Unit = {}
) = composable<Route.LabGraph.List> {
    onUiStateChange(LabListUiState)

    LabScreen(
        navigateToAeadEncryption = navigateToAeadEncryption,
        navigateToCombinedZip = navigateToCombinedZip
    )
}