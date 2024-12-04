package com.nevidimka655.astracrypt.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.composables.about.aboutGraph
import com.nevidimka655.astracrypt.ui.navigation.composables.details
import com.nevidimka655.astracrypt.ui.navigation.composables.export
import com.nevidimka655.astracrypt.ui.navigation.composables.settings.editProfile
import com.nevidimka655.astracrypt.ui.navigation.composables.settings.settingsUi
import com.nevidimka655.astracrypt.ui.navigation.composables.tabs
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
    aboutGraph(onUiStateChange = onUiStateChange, navController = navController)
}