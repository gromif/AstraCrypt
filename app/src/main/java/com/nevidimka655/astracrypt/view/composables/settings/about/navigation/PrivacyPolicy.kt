package com.nevidimka655.astracrypt.view.composables.settings.about.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.composables.settings.about.privacy_policy.PrivacyPolicyScreen
import com.nevidimka655.astracrypt.view.composables.settings.about.privacy_policy.PrivacyPolicyViewModel
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

val PrivacyPolicyUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.privacyPolicy)
    )
)

fun NavGraphBuilder.privacyPolicy(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.AboutGraph.PrivacyPolicy> {
    onUiStateChange(PrivacyPolicyUiState)
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