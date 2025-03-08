package io.gromif.astracrypt.presentation.navigation.lab

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.lab.tink.labTinkGraph

internal fun NavGraphBuilder.labGraph() = navigation<Route.LabGraph>(startDestination = Route.LabGraph.List) {
    labList()
    labTinkGraph()
    labCombinedZip()
}