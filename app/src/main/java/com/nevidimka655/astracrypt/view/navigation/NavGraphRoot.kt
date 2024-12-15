package com.nevidimka655.astracrypt.view.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nevidimka655.astracrypt.view.models.ToolbarAction
import com.nevidimka655.astracrypt.view.MainVM
import com.nevidimka655.astracrypt.view.composables.files.details.details
import com.nevidimka655.astracrypt.view.composables.notes.notesGraph
import com.nevidimka655.astracrypt.view.composables.settings.about.navigation.aboutGraph
import com.nevidimka655.astracrypt.view.composables.settings.profile.editProfile
import com.nevidimka655.astracrypt.view.composables.settings.security.admin.settingsSecurityAdmin
import com.nevidimka655.astracrypt.view.composables.settings.security.auth.settingsSecurityAuth
import com.nevidimka655.astracrypt.view.composables.settings.security.quick_actions.settingsSecurityQuickActions
import com.nevidimka655.astracrypt.view.composables.settings.security.settingsSecurity
import com.nevidimka655.astracrypt.view.composables.settings.ui.files.settingsUiFiles
import com.nevidimka655.astracrypt.view.composables.settings.ui.settingsUi
import com.nevidimka655.astracrypt.view.composables.shared.export.export
import com.nevidimka655.astracrypt.view.composables.tabs
import com.nevidimka655.astracrypt.view.models.UiState
import kotlinx.coroutines.channels.Channel

inline fun root(
    crossinline onUiStateChange: (UiState) -> Unit,
    vm: MainVM,
    navController: NavController,
    onFabClick: Channel<Any>,
    onToolbarActions: Channel<ToolbarAction>
): NavGraphBuilder.() -> Unit = {
    tabs(
        onUiStateChange = onUiStateChange,
        vm = vm,
        navController = navController,
        onFabClick = onFabClick,
        onToolbarActions = onToolbarActions
    )
    notesGraph(
        onUiStateChange = onUiStateChange,
        navController = navController,
        onFabClick = onFabClick
    )
    details(onUiStateChange = onUiStateChange)
    export(onUiStateChange = onUiStateChange)

    // settings
    editProfile(onUiStateChange = onUiStateChange)
    settingsUi(
        onUiStateChange = onUiStateChange,
        navigateToFilesUiSettings = { navController.navigate(Route.SettingsUiFiles) }
    )
    settingsUiFiles(onUiStateChange = onUiStateChange)
    settingsSecurity(
        onUiStateChange = onUiStateChange,
        navigateToEncryption = {},
        navigateToAuth = { navController.navigate(Route.SettingsSecurityAuth) },
        navigateToDeviceAdmin = { navController.navigate(Route.SettingsSecurityAdmin) },
        navigateToQuickActions = { navController.navigate(Route.SettingsSecurityQuickActions) }
    )
    settingsSecurityAdmin(onUiStateChange = onUiStateChange)
    settingsSecurityAuth(onUiStateChange = onUiStateChange)
    settingsSecurityQuickActions(onUiStateChange = onUiStateChange)
    aboutGraph(onUiStateChange = onUiStateChange, navController = navController)
}