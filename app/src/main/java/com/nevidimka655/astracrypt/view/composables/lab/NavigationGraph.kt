package com.nevidimka655.astracrypt.view.composables.lab

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.composables.lab.tink.labTinkGraph
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route

inline fun NavGraphBuilder.labGraph(
    crossinline onUiStateChange: (UiState) -> Unit,
    navController: NavController
) = navigation<Route.LabGraph>(startDestination = Route.LabGraph.List) {
    labList(
        onUiStateChange = onUiStateChange,
        navigateToAeadEncryption = { navController.navigate(Route.LabGraph.TinkGraph) },
        navigateToCombinedZip = { TODO() },
    )
    labTinkGraph(onUiStateChange = onUiStateChange, navController = navController)
}