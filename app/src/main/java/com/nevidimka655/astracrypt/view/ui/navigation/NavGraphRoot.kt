package com.nevidimka655.astracrypt.view.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nevidimka655.astracrypt.view.UiState
import com.nevidimka655.astracrypt.view.ui.MainVM
import com.nevidimka655.astracrypt.view.ui.composables.about.aboutGraph
import com.nevidimka655.astracrypt.view.ui.composables.details
import com.nevidimka655.astracrypt.view.ui.composables.export
import com.nevidimka655.astracrypt.view.ui.composables.settings.editProfile
import com.nevidimka655.astracrypt.view.ui.composables.settings.settingsSecurity
import com.nevidimka655.astracrypt.view.ui.composables.settings.settingsUi
import com.nevidimka655.astracrypt.view.ui.composables.tabs
import kotlinx.coroutines.channels.Channel

inline fun root(
    crossinline onUiStateChange: (UiState) -> Unit,
    vm: MainVM,
    navController: NavController,
    onFabClick: Channel<Any>
): NavGraphBuilder.() -> Unit = {
    tabs(
        onUiStateChange = onUiStateChange,
        vm = vm,
        navController = navController,
        onFabClick = onFabClick
    )
    details(onUiStateChange = onUiStateChange)
    export(onUiStateChange = onUiStateChange)

    // settings
    editProfile(onUiStateChange = onUiStateChange)
    settingsUi(
        onUiStateChange = onUiStateChange,
        navigateToFilesUiSettings = {  }
    )
    settingsSecurity(onUiStateChange = onUiStateChange)
    aboutGraph(onUiStateChange = onUiStateChange, navController = navController)
}