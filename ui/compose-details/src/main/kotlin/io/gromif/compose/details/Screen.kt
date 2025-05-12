package io.gromif.compose.details

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gromif.compose.details.model.DetailsGroup
import io.gromif.compose.details.shared.DetailsList
import io.gromif.compose.details.shared.FakeData
import io.gromif.compose.details.shared.GroupList
import io.gromif.compose.details.shared.Header
import io.gromif.compose.details.shared.groups
import io.gromif.ui.compose.core.TwoPanelLayoutInnerScroll

@Composable
fun DetailsScreen(
    groups: SnapshotStateList<DetailsGroup>,
    headerImage: @Composable BoxScope.() -> Unit,
    title: String,
    showGroups: Boolean = true,
) {
    var selectedGroup by remember { mutableIntStateOf(0) }
    TwoPanelLayoutInnerScroll(
        padding = 0.dp,
        left = {
            Header(
                headerImage = headerImage,
                title = title
            )
            if (showGroups) GroupList(
                groups = groups,
                selectedGroup = selectedGroup,
                onGroupSelected = { selectedGroup = it }
            )
        }, right = {
            DetailsList(items = groups[selectedGroup].detailsItemList)
        })
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenPreview(
    groups: SnapshotStateList<DetailsGroup> = mutableDetailsStateList().also {
        FakeData.groups(it)
    },
    headerImage: @Composable BoxScope.() -> Unit = {},
    title: String = "Title",
    showGroups: Boolean = true,
) = DetailsScreen(
    groups = groups,
    headerImage = headerImage,
    title = title,
    showGroups = showGroups
)