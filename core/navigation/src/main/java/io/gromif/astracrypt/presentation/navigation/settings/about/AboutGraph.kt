package io.gromif.astracrypt.presentation.navigation.settings.about

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import io.gromif.astracrypt.presentation.navigation.Route

fun NavGraphBuilder.aboutGraph(
    applicationVersion: String,
) = navigation<Route.AboutGraph>(startDestination = Route.AboutGraph.About) {
    about(applicationVersion = applicationVersion)
    privacyPolicy()
}