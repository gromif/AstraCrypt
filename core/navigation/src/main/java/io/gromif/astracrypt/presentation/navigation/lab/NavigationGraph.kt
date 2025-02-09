package io.gromif.astracrypt.presentation.navigation.lab

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.lab.tink.labTinkGraph
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
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