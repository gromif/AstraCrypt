package com.nevidimka655.astracrypt.view.composables.lab.tink

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route

inline fun NavGraphBuilder.labTinkGraph(
    crossinline onUiStateChange: (UiState) -> Unit,
    navController: NavController
) = navigation<Route.LabGraph.TinkGraph>(startDestination = Route.LabGraph.TinkGraph.Key) {
    tinkKey(onUiStateChange = onUiStateChange)
}