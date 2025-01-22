package io.gromif.astracrypt.files.shared.list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.NoItemsPage

@Composable
fun EmptyList(
    isStarredScreen: Boolean,
    isSearching: Boolean
) = when {
    isStarredScreen -> NoItemsPage(
        mainIcon = Icons.Outlined.StarOutline,
        actionIcon = Icons.Outlined.StarOutline,
        title = stringResource(R.string.noItemsTitle),
        summary = stringResource(R.string.noItemsSummary)
    )

    isSearching -> NoItemsPage(
        mainIcon = Icons.Filled.Search,
        actionIcon = Icons.Filled.Search,
        title = stringResource(R.string.noItemsTitleSearch),
        summary = stringResource(R.string.noItemsSummarySearch)
    )

    else -> NoItemsPage(
        title = stringResource(R.string.noItemsTitle),
        summary = stringResource(R.string.noItemsSummary)
    )
}