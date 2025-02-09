package io.gromif.astracrypt.presentation.navigation.lab.tink

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.tink_lab.TinkLab
import com.nevidimka655.tink_lab.files.FilesScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler

private typealias ComposableRoute = Route.LabGraph.TinkGraph.Files

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(title = TextWrap.Resource(id = R.string.files))
)

internal fun NavGraphBuilder.tinkFiles(
    onUiStateChange: (UiState) -> Unit
) = composable<ComposableRoute> {
    UiStateHandler { onUiStateChange(ScreenUiState) }
    val route: ComposableRoute = it.toRoute()

    TinkLab.FilesScreen(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        rawKeyset = route.rawKeyset
    )
}