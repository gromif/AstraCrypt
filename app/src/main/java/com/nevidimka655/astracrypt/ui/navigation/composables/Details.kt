package com.nevidimka655.astracrypt.ui.navigation.composables

import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.features.details.DetailsScreen
import com.nevidimka655.astracrypt.features.details.DetailsScreenViewModel
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.Route

inline fun NavGraphBuilder.details(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.Tabs.Details> {
    val details: Route.Tabs.Details = it.toRoute()
    onUiStateChange(Route.Tabs.Details.Ui.state)
    val context = LocalContext.current
    val vm: DetailsScreenViewModel = hiltViewModel()
    DetailsScreen(
        detailsManager = vm.detailsManager,
        imageLoader = vm.imageLoader,
        onStart = {
            if (vm.detailsManager.list.isEmpty()) {
                vm.submitDetailsQuery(context = context, itemId = details.itemId)
            }
        }
    )
}