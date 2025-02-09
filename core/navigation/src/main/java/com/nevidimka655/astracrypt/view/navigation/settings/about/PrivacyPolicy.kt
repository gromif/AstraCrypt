package com.nevidimka655.astracrypt.view.navigation.settings.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.Route
import com.nevidimka655.astracrypt.view.navigation.models.UiState
import com.nevidimka655.astracrypt.view.navigation.shared.UiStateHandler
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.settings.privacy.PrivacyPolicyScreen

private val PrivacyPolicyUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.privacyPolicy)
    )
)

fun NavGraphBuilder.privacyPolicy(
    onUiStateChange: (UiState) -> Unit
) = composable<Route.AboutGraph.PrivacyPolicy> {
    UiStateHandler { onUiStateChange(PrivacyPolicyUiState) }
    PrivacyPolicyScreen(modifier = Modifier.fillMaxSize())
}