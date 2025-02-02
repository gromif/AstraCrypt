package com.nevidimka655.astracrypt.view.navigation.tabs.files

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.details.FilesDetailsScreen

val DetailsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files_options_details)
    )
)

fun NavGraphBuilder.details(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.Details> {
    val details: Route.Details = it.toRoute()
    UiStateHandler { onUiStateChange(DetailsUiState) }
    FilesDetailsScreen(id = details.id)
}