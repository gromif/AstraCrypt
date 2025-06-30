package io.gromif.astracrypt.settings.about.shared

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.gromif.astracrypt.settings.about.list.FakeData
import io.gromif.astracrypt.settings.about.list.LinkList
import io.gromif.astracrypt.settings.about.model.Link

@Composable
internal fun CardLinkList(
    modifier: Modifier = Modifier,
    links: List<Link> = FakeData.linkList(),
    onLinkClick: (Link) -> Unit = {},
) = Card(modifier = modifier) {
    LinkList(links = links, onClick = onLinkClick)
}
