package com.nevidimka655.astracrypt.view.composables.lab

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val LabListUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.lab)
    )
)

inline fun NavGraphBuilder.labList(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.Lab.List> {
    onUiStateChange(LabListUiState)

    LabScreen(
        navigateToAeadEncryption = {},
        navigateToCombinedZip = {}
    )
}