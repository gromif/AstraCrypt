package io.gromif.astracrypt.presentation.navigation.lab

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

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