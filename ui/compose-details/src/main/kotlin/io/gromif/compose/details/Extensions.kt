package io.gromif.compose.details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.compose.details.model.DetailsGroup
import io.gromif.compose.details.model.DetailsItem
import io.gromif.ui.compose.core.wrappers.TextWrap

fun mutableDetailsStateList() = mutableStateListOf<DetailsGroup>()

inline fun SnapshotStateList<DetailsGroup>.addGroup(
    name: TextWrap,
    groupBuilder: MutableList<DetailsItem>.() -> Unit
) {
    val list = mutableListOf<DetailsItem>().apply { groupBuilder(this) }.toList()
    add(DetailsGroup(name, list))
}

fun MutableList<DetailsItem>.addItem(
    icon: ImageVector,
    title: TextWrap,
    summary: TextWrap
) = add(DetailsItem(icon = icon, title = title, summary = summary))