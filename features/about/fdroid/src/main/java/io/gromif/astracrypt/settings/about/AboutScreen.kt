package io.gromif.astracrypt.settings.about

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.about.model.Link
import io.gromif.astracrypt.settings.about.shared.PredefinedLinks

@Composable
fun AboutScreen(
    snackbarHostState: SnackbarHostState,
    version: String,
    toPrivacyPolicy: () -> Unit,
) = About(
    params = buildAboutParams(version),
    snackbarHostState = snackbarHostState,
    toPrivacyPolicy = toPrivacyPolicy
)

@Composable
private fun buildAboutParams(version: String) = Params(
    version = version,
    commonLinks = listOf(
        PredefinedLinks.Market.Play.otherAppsLink(
            name = stringResource(R.string.about_moreApps),
            description = stringResource(R.string.about_moreApps_summary)
        ),
        Link.Default(
            name = stringResource(R.string.about_leaveFeedback),
            link = PredefinedLinks.Market.GitHub.appLink.link
        ),
        Link.PrivacyPolicy
    ),
    supportLinks = listOf(
        PredefinedLinks.Communication.email,
        PredefinedLinks.Market.GitHub.appLink
    )
)
