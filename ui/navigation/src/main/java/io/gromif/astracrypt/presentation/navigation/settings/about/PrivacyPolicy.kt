package io.gromif.astracrypt.presentation.navigation.settings.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.models.UiState
import io.gromif.astracrypt.presentation.navigation.shared.LocalHostEvents
import io.gromif.astracrypt.presentation.navigation.shared.UiStateHandler
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.privacy.PrivacyPolicyScreen
import io.gromif.ui.compose.core.theme.spaces
import io.gromif.ui.compose.core.wrappers.TextWrap

private val DefaultUiState = UiState(
    toolbar = UiState.Toolbar(
        title = TextWrap.Resource(id = R.string.privacyPolicy)
    )
)

internal fun NavGraphBuilder.privacyPolicy() = composable<Route.AboutGraph.PrivacyPolicy> {
    val hostEvents = LocalHostEvents.current
    UiStateHandler { hostEvents.setUiState(DefaultUiState) }

    PrivacyPolicyScreen(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spaces.spaceMedium)
    )
}
