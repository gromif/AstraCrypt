package com.nevidimka655.astracrypt.view.composables.lab.tink

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.labTinkGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onFabClick: Flow<Any>
) = navigation<Route.LabGraph.TinkGraph>(startDestination = Route.LabGraph.TinkGraph.Key) {
    tinkKey(onUiStateChange = onUiStateChange, onFabClick = onFabClick)
}