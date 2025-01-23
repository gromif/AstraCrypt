package com.nevidimka655.astracrypt.view.navigation.help

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.compose_help.HelpItem
import com.nevidimka655.compose_help.HelpScreen
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import kotlinx.serialization.json.Json

private typealias ComposableRoute = Route.Help

private val ScreenUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.help)
    )
)

fun NavGraphBuilder.help(
    onUiStateChange: (UiState) -> Unit
) = composable<ComposableRoute> {
    UiStateHandler { onUiStateChange(ScreenUiState) }
    val help: ComposableRoute = it.toRoute()
    val helpList: List<HelpItem> = remember { Json.decodeFromString(help.helpList) }

    HelpScreen(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        list = helpList
    )
}