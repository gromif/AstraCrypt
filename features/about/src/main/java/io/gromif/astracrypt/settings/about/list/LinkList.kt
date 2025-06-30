package io.gromif.astracrypt.settings.about.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.about.model.Link
import io.gromif.ui.compose.core.AutoLineListItem
import io.gromif.ui.compose.core.OneLineListItem

@Composable
internal fun LinkList(
    links: List<Link>,
    onClick: (Link) -> Unit
) = links.fastForEach {
    val onClick = { onClick(it) }
    when (it) {
        is Link.Default -> AutoLineListItem(
            titleText = it.name,
            summaryText = it.description,
            onClick = onClick
        )

        Link.PrivacyPolicy -> OneLineListItem(
            titleText = stringResource(id = R.string.privacyPolicy),
            onClick = onClick
        )

        is Link.Email -> AutoLineListItem(
            titleText = it.name,
            summaryText = it.description,
            onClick = onClick
        )
    }
}
