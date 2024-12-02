package com.nevidimka655.astracrypt.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.composables.export
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
    export(onUiStateChange = onUiStateChange)
}