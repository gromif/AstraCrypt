package io.gromif.astracrypt.settings.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.about.list.FakeData
import io.gromif.astracrypt.settings.about.list.LinkList
import io.gromif.astracrypt.settings.about.model.Link
import io.gromif.astracrypt.settings.about.shared.CardLinkList
import io.gromif.astracrypt.settings.about.shared.Header
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen

@Preview(showBackground = true)
@Composable
fun Screen(
    params: Params = Params(),
    commonLinks: List<Link> = FakeData.linkList(),
    supportLinks: List<Link> = FakeData.linkList(),
    onLinkClick: (Link) -> Unit = {},
    onMoreApps: () -> Unit = {},
    onLeaveFeedback: () -> Unit = {},
    onEmailClick: () -> Unit = {},
    onMarketClick: () -> Unit = {},
    toPrivacyPolicy: () -> Unit = {},
) = PreferencesScreen {
    Header(version = params.version)
    CardLinkList(links = commonLinks, onLinkClick = onLinkClick)
    PreferencesGroup(text = stringResource(id = R.string.support)) {
        LinkList(links = supportLinks, onClick = onLinkClick)
    }
}
