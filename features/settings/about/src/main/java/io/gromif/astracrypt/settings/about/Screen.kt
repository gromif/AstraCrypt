package io.gromif.astracrypt.settings.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import io.gromif.astracrypt.settings.about.shared.CommonOptions
import io.gromif.astracrypt.settings.about.shared.CommunicationOptions
import io.gromif.astracrypt.settings.about.shared.Header

@Preview(showBackground = true)
@Composable
fun Screen(
    params: Params = Params(),
    onMoreApps: () -> Unit = {},
    onLeaveFeedback: () -> Unit = {},
    onEmailClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    toPrivacyPolicy: () -> Unit = {},
) = PreferencesScreen {
    Header(version = params.version)
    CommonOptions(
        onMoreApps = onMoreApps,
        onLeaveFeedback = onLeaveFeedback,
        toPrivacyPolicy = toPrivacyPolicy
    )
    PreferencesGroup(text = stringResource(id = R.string.support)) {
        CommunicationOptions(
            storeName = params.storeName,
            onEmailClick = onEmailClick,
            onMarketClick = onMarketClick
        )
    }
}

