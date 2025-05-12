package io.gromif.compose.details.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.gromif.compose.details.addGroup
import io.gromif.compose.details.addItem
import io.gromif.compose.details.model.DetailsGroup
import io.gromif.ui.compose.core.wrappers.TextWrap

internal object FakeData

internal fun FakeData.groups(
    stateList: SnapshotStateList<DetailsGroup>
) = repeat(7) { group ->
    stateList.addGroup(name = TextWrap.Text("Group$group")) {
        repeat(5) {
            addItem(
                icon = Icons.Default.Details,
                title = TextWrap.Text("Title #$it [Group #$group]"),
                summary = TextWrap.Text("summery #$it")
            )
        }
    }
}