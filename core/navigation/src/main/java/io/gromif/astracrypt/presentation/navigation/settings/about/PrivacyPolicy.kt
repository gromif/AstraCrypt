package io.gromif.astracrypt.presentation.navigation.settings.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
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