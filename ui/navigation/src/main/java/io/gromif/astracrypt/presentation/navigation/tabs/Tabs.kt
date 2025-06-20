package io.gromif.astracrypt.presentation.navigation.tabs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.presentation.navigation.BottomBarItems
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.presentation.navigation.tabs.home.tabHome
import io.gromif.astracrypt.presentation.navigation.tabs.settings.SettingsScreen
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.wrappers.TextWrap

internal fun NavGraphBuilder.tabsGraph() {
    tabFiles()
    tabStarred()
    tabHome()
    composable<Route.Tabs.Settings> {
        val hostEvents = LocalHostEvents.current
        UiStateHandler { hostEvents.setUiState(SettingsUiState) }

        SettingsScreen()
    }
}

private val SettingsUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.settings)
    ),
    bottomBarTab = BottomBarItems.Settings
)
