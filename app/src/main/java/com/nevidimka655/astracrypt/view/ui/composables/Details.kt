package com.nevidimka655.astracrypt.view.ui.composables

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.details.DetailsScreen
import com.nevidimka655.astracrypt.features.details.DetailsScreenViewModel
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val DetailsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.files_options_details)
    )
)

inline fun NavGraphBuilder.details(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.Details> {
    val details: Route.Details = it.toRoute()
    onUiStateChange(DetailsUiState)
    val context = LocalContext.current
    val vm: DetailsScreenViewModel = hiltViewModel()
    DetailsScreen(
        detailsManager = vm.detailsManager,
        preview = vm.preview,
        imageLoader = vm.imageLoader,
        itemType = vm.type,
        onStart = {
            if (vm.detailsManager.list.isEmpty()) {
                vm.submitDetailsQuery(context = context, itemId = details.itemId)
            }
        }
    )
}