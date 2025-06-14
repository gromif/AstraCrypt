package io.gromif.compose.details.shared

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.gromif.compose.details.model.DetailsGroup
import io.gromif.ui.compose.core.chips.Chips
import io.gromif.ui.compose.core.chips.Filter
import io.gromif.ui.compose.core.theme.spaces

@Composable
internal fun GroupList(
    groups: SnapshotStateList<DetailsGroup> = mutableStateListOf(),
    selectedGroup: Int,
    onGroupSelected: (Int) -> Unit
) = Row(
    modifier = Modifier
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = MaterialTheme.spaces.spaceMedium),
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    val context = LocalContext.current
    groups.forEachIndexed { i, it ->
        Chips.Filter(
            selected = i == selectedGroup,
            onClick = { onGroupSelected(i) },
            label = it.title.resolve(context)
        )
    }
}