package com.nevidimka655.astracrypt.view.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nevidimka655.astracrypt.view.composables.export.export
import com.nevidimka655.astracrypt.view.composables.files.details.details
import com.nevidimka655.astracrypt.view.composables.settings.about.navigation.aboutGraph
import com.nevidimka655.astracrypt.view.composables.settings.profile.editProfile
import com.nevidimka655.astracrypt.view.composables.settings.security.admin.settingsSecurityAdmin
import com.nevidimka655.astracrypt.view.composables.settings.security.quick_actions.settingsSecurityQuickActions
import com.nevidimka655.astracrypt.view.composables.settings.security.settingsSecurity
import com.nevidimka655.astracrypt.view.composables.settings.ui.files.settingsUiFiles
import com.nevidimka655.astracrypt.view.composables.settings.ui.settingsUi
import com.nevidimka655.astracrypt.view.composables.tabsGraph
import com.nevidimka655.astracrypt.view.navigation.auth.settingsSecurityAuth
import com.nevidimka655.astracrypt.view.navigation.help.help
import com.nevidimka655.astracrypt.view.navigation.lab.labGraph
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.astracrypt.view.navigation.notes.notesGraph
import com.nevidimka655.astracrypt.view.navigation.settings.settingsSecurityAead
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

fun root(
    onUiStateChange: (UiState) -> Unit,
    navController: NavController,
    onFabClick: Flow<Any>,
    onToolbarActions: Flow<ToolbarActions.Action>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>
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
    editProfile(onUiStateChange = onUiStateChange)
    settingsUi(
        onUiStateChange = onUiStateChange,
        navigateToFilesUiSettings = { navController.navigate(Route.SettingsUiFiles) }
    )
    settingsUiFiles(onUiStateChange = onUiStateChange)
    settingsSecurity(
        onUiStateChange = onUiStateChange,
        navigateToAead = { navController.navigate(Route.SettingsSecurityAead) },
        navigateToAuth = { navController.navigate(Route.SettingsSecurityAuth) },
        navigateToDeviceAdmin = { navController.navigate(Route.SettingsSecurityAdmin) },
        navigateToQuickActions = { navController.navigate(Route.SettingsSecurityQuickActions) }
    )
    settingsSecurityAdmin(onUiStateChange = onUiStateChange)
    settingsSecurityAuth(onUiStateChange = onUiStateChange)
    settingsSecurityAead(onUiStateChange = onUiStateChange)
    settingsSecurityQuickActions(onUiStateChange = onUiStateChange)
    aboutGraph(onUiStateChange = onUiStateChange, navController = navController)

    help(onUiStateChange = onUiStateChange)
}