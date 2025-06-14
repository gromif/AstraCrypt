package io.gromif.compose.details.shared

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import io.gromif.compose.details.model.DetailsItem
import io.gromif.ui.compose.core.ColoredIconSecondary
import io.gromif.ui.compose.core.TwoLineListItem

@Composable
internal fun DetailsList(items: List<DetailsItem>) {
    val context = LocalContext.current
    items.forEach {
        DetailsListItem(
            icon = it.icon,
            title = it.title.resolve(context),
            summary = it.summary.resolve(context)
        )
    }
}

@Composable
private fun DetailsListItem(
    icon: ImageVector?,
    title: String,
    summary: String
) {
    TwoLineListItem(
        leadingContent = icon?.let {
            @Composable { ColoredIconSecondary(imageVector = icon) }
        },
        titleText = title,
        summaryText = summary
    )
}