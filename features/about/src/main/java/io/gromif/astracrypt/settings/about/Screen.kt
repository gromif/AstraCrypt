package io.gromif.astracrypt.settings.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.about.list.LinkList
import io.gromif.astracrypt.settings.about.model.Link
import io.gromif.astracrypt.settings.about.model.Params
import io.gromif.astracrypt.settings.about.shared.CardLinkList
import io.gromif.astracrypt.settings.about.shared.Header
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.PreferencesScreen

@Preview(showBackground = true)
@Composable
fun Screen(
    params: Params = Params(),
    onLinkClick: (Link) -> Unit = {},
) = PreferencesScreen {
    Header(version = params.version)
    CardLinkList(links = params.commonLinks, onLinkClick = onLinkClick)
    PreferencesGroup(text = stringResource(id = R.string.support)) {
        LinkList(links = params.supportLinks, onClick = onLinkClick)
    }
}
