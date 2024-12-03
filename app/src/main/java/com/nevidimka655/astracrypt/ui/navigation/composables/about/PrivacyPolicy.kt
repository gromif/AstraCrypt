package com.nevidimka655.astracrypt.ui.navigation.composables.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.ui.UiState
import com.nevidimka655.astracrypt.ui.navigation.Route
import com.nevidimka655.astracrypt.ui.tabs.settings.about.privacy_policy.PrivacyPolicyScreen
import com.nevidimka655.astracrypt.ui.tabs.settings.about.privacy_policy.PrivacyPolicyViewModel

inline fun NavGraphBuilder.privacyPolicy(
    crossinline onUiStateChange: (UiState) -> Unit
) = composable<Route.AboutGraph.PrivacyPolicy> {
    onUiStateChange(Route.AboutGraph.PrivacyPolicy.Ui.state)
    val context = LocalContext.current
    val vm: PrivacyPolicyViewModel = viewModel()
    LaunchedEffect(Unit) {
        if (vm.html.isEmpty()) vm.load(context = context)
    }
    PrivacyPolicyScreen(
        modifier = Modifier.fillMaxSize(),
        privacyPolicyHtml = vm.html
    )
}