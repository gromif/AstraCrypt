package io.gromif.astracrypt.presentation.navigation.lab.tink

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import io.gromif.astracrypt.presentation.navigation.Route

private typealias Graph = Route.LabGraph.TinkGraph
private typealias Key = Route.LabGraph.TinkGraph.Key

internal fun NavGraphBuilder.labTinkGraph() = navigation<Graph>(startDestination = Key) {
    tinkKey()
    tinkText()
    tinkFiles()
}
