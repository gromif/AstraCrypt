package io.gromif.astracrypt.files.files.model.action.factory

import androidx.compose.runtime.snapshots.SnapshotStateList
import io.gromif.astracrypt.files.files.FilesViewModel
import io.gromif.astracrypt.files.files.model.action.Actions
import io.gromif.astracrypt.files.files.model.action.ItemActions
import io.gromif.astracrypt.resources.R

internal fun Actions.createItemActions(
    vm: FilesViewModel,
    onMessage: (Int) -> Unit,
    multiselectStateList: SnapshotStateList<Long>,
): ItemActions = object : ItemActions() {
    override fun star(state: Boolean, idList: List<Long>) {
        vm.setStarred(state, idList)
        onMessage(if (state) R.string.snack_starred else R.string.snack_unstarred)
    }

    override fun rename(id: Long, name: String) {
        vm.rename(id, name).invokeOnCompletion {
            onMessage(R.string.snack_itemRenamed)
        }
    }

    override fun createFolder(name: String) {
        vm.createFolder(name).invokeOnCompletion {
            onMessage(R.string.snack_folderCreated)
        }
    }

    override fun move() {
        vm.move(ids = multiselectStateList.toList()).invokeOnCompletion {
            onMessage(R.string.snack_itemsMoved)
        }
    }

    override fun delete(idList: List<Long>) {
        vm.delete(idList).invokeOnCompletion {
            onMessage(R.string.snack_itemsDeleted)
        }
    }
}
