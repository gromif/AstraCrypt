package io.gromif.astracrypt.files.files.model.action

import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.files.model.Mode

@Stable
internal open class BrowseActions {

    @Stable
    data class State(
        val mode: Mode,
        val multiselectStateList: SnapshotStateList<Long>,
        val isStarred: Boolean,
    )

    open fun backStackClick(folder: StorageNavigator.Folder?) {}
    open fun open(id: Long) {}
    open fun click(item: Item) {}
    open fun longClick(id: Long) {}
}
