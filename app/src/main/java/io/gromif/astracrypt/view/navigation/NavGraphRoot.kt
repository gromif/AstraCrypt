package io.gromif.astracrypt.view.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nevidimka655.astracrypt.BuildConfig
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.auth.settingsSecurityAuth
import io.gromif.astracrypt.presentation.navigation.help.help
import io.gromif.astracrypt.presentation.navigation.lab.labGraph
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.notes.notesGraph
import io.gromif.astracrypt.presentation.navigation.settings.about.aboutGraph
import io.gromif.astracrypt.presentation.navigation.settings.aead.settingsSecurityAead
import io.gromif.astracrypt.presentation.navigation.settings.aead.settingsSecurityColumnsAead
import io.gromif.astracrypt.presentation.navigation.settings.profileSettings
import io.gromif.astracrypt.presentation.navigation.settings.quickActionsSettings
import io.gromif.astracrypt.presentation.navigation.settings.security.settingsSecurity
import io.gromif.astracrypt.presentation.navigation.settings.settingsSecurityAdmin
import io.gromif.astracrypt.presentation.navigation.settings.ui.filesUiSettings
import io.gromif.astracrypt.presentation.navigation.settings.ui.settingsUi
import io.gromif.astracrypt.presentation.navigation.tabs.files.details
import io.gromif.astracrypt.presentation.navigation.tabs.files.export
import io.gromif.astracrypt.presentation.navigation.tabs.tabsGraph
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

fun root(
    onUiStateChange: (UiState) -> Unit,
    isActionsSupported: Boolean,
    navController: NavController,
    onFabClick: Flow<Any>,
    onToolbarActions: Flow<ToolbarActions.Action>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>,
    dynamicThemeState: Boolean,
    isDynamicColorsSupported: Boolean,
    onDynamicColorsStateChange: (Boolean) -> Unit,
): NavGraphBuilder.() -> Unit = {
    tabsGraph(
        onUiStateChange = onUiStateChange,
        navController = navController,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick,
        snackbarHostState = snackbarHostState,
        searchQueryState = searchQueryState
    )
    notesGraph(
        onUiStateChange = onUiStateChange,
        navController = navController,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick
    )
    details(onUiStateChange = onUiStateChange)
    export(onUiStateChange = onUiStateChange)

    labGraph(
        onUiStateChange = onUiStateChange,
        navController = navController,
        onToolbarActions = onToolbarActions,
        onFabClick = onFabClick
    )

    // settings
    profileSettings(onUiStateChange = onUiStateChange)
    settingsUi(
        onUiStateChange = onUiStateChange,
        navigateToFilesUiSettings = { navController.navigate(Route.SettingsUiFiles) },
        dynamicThemeState = dynamicThemeState,
        isDynamicColorsSupported = isDynamicColorsSupported,
        onDynamicColorsStateChange = onDynamicColorsStateChange,
    )
    filesUiSettings(onUiStateChange = onUiStateChange)
    settingsSecurity(
        onUiStateChange = onUiStateChange,
        isActionsSupported = isActionsSupported,
        navigateToAead = { navController.navigate(Route.SettingsSecurityAead) },
        navigateToAuth = { navController.navigate(Route.SettingsSecurityAuth) },
        navigateToDeviceAdmin = { navController.navigate(Route.SettingsSecurityAdmin) },
        navigateToQuickActions = { navController.navigate(Route.SettingsSecurityQuickActions) }
    )
    settingsSecurityAdmin(onUiStateChange = onUiStateChange)
    settingsSecurityAuth(onUiStateChange = onUiStateChange)
    settingsSecurityAead(
        navController = navController,
        onUiStateChange = onUiStateChange
    )
    settingsSecurityColumnsAead(
        onUiStateChange = onUiStateChange,
        onToolbarActions = onToolbarActions
    )
    quickActionsSettings(onUiStateChange = onUiStateChange)
    aboutGraph(
        onUiStateChange = onUiStateChange,
        snackbarHostState = snackbarHostState,
        navController = navController,
        applicationVersion = BuildConfig.VERSION_NAME
    )

    help(onUiStateChange = onUiStateChange)
}