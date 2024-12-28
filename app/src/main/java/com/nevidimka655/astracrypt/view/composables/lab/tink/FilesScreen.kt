package com.nevidimka655.astracrypt.view.composables.lab.tink

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.tink_lab.TinkLab
import com.nevidimka655.tink_lab.files.FilesScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

private typealias ComposableRoute = Route.LabGraph.TinkGraph.Files

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(title = TextWrap.Resource(id = R.string.files))
)

fun NavGraphBuilder.tinkFiles(
    onUiStateChange: (UiState) -> Unit
) = composable<ComposableRoute> {
    onUiStateChange(ScreenUiState)
    val route: ComposableRoute = it.toRoute()

    TinkLab.FilesScreen(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        rawKeyset = route.rawKeyset
    )
}