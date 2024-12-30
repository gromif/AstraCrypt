package com.nevidimka655.astracrypt.view.navigation.lab.tink

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import kotlinx.coroutines.flow.Flow

private typealias Graph = Route.LabGraph.TinkGraph
private typealias Key = Route.LabGraph.TinkGraph.Key
private typealias Text = Route.LabGraph.TinkGraph.Text
private typealias Files = Route.LabGraph.TinkGraph.Files

internal fun NavGraphBuilder.labTinkGraph(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onFabClick: Flow<Any>
) = navigation<Graph>(startDestination = Key) {
    tinkKey(
        onUiStateChange = onUiStateChange,
        onFabClick = onFabClick,
        navigateToTextMode = { navController.navigate(Text(rawKeyset = it)) },
        navigateToFilesMode = { navController.navigate(Files(rawKeyset = it)) }
    )
    tinkText(onUiStateChange = onUiStateChange)
    tinkFiles(onUiStateChange = onUiStateChange)
}