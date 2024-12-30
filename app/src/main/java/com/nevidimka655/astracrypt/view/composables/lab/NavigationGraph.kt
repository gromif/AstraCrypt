package com.nevidimka655.astracrypt.view.composables.lab

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.composables.lab.tink.labTinkGraph
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.labGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onToolbarActions: Flow<ToolbarActions.Action>,
    onFabClick: Flow<Any>
) = navigation<Route.LabGraph>(startDestination = Route.LabGraph.List) {
    labList(
        onUiStateChange = onUiStateChange,
        navigateToAeadEncryption = { navController.navigate(Route.LabGraph.TinkGraph) },
        navigateToCombinedZip = { navController.navigate(Route.LabGraph.CombinedZip) },
    )
    labTinkGraph(
        onUiStateChange = onUiStateChange,
        navController = navController,
        onFabClick = onFabClick
    )
    labCombinedZip(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        navigateToHelp = {
            navController.navigate(
                Route.Help(helpList = Json.encodeToString(it))
            )
        }
    )
}