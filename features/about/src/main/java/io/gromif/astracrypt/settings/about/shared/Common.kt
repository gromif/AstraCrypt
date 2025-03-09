package io.gromif.astracrypt.settings.about.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import io.gromif.ui.compose.core.OneLineListItem
import io.gromif.ui.compose.core.TwoLineListItem

@Composable
internal fun CommonOptions(
    onMoreApps: () -> Unit,
    onLeaveFeedback: () -> Unit,
    toPrivacyPolicy: () -> Unit
) = Card(modifier = Modifier.fillMaxWidth()) {
    TwoLineListItem(
        titleText = stringResource(id = R.string.about_moreApps),
        summaryText = stringResource(id = R.string.about_moreApps_summary),
        onClick = onMoreApps
    )
    OneLineListItem(
        titleText = stringResource(id = R.string.about_leaveFeedback),
        onClick = onLeaveFeedback
    )
    OneLineListItem(
        titleText = stringResource(id = R.string.privacyPolicy),
        onClick = toPrivacyPolicy
    )
}