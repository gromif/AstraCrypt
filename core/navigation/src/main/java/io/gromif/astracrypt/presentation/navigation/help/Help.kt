package io.gromif.astracrypt.presentation.navigation.help

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.compose_help.HelpItem
import com.nevidimka655.compose_help.HelpScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import kotlinx.serialization.json.Json

private typealias ComposableRoute = Route.Help

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.help)
    )
)

fun NavGraphBuilder.help() = composable<ComposableRoute> {
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    val help: ComposableRoute = it.toRoute()
    val helpList: List<HelpItem> = remember { Json.decodeFromString(help.helpList) }

    HelpScreen(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        list = helpList
    )
}