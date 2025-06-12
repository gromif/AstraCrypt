package io.gromif.astracrypt.files.files.model.action.factory

import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.files.FilesViewModel
import io.gromif.astracrypt.files.files.model.Mode
import io.gromif.astracrypt.files.files.model.action.Actions
import io.gromif.astracrypt.files.files.model.action.BrowseActions
import io.gromif.astracrypt.files.files.model.action.FilesNavActions

internal fun Actions.createBrowseActions(
    vm: FilesViewModel,
    navActions: FilesNavActions,
    state: BrowseActions.State,
    onSelectItem: (Long) -> Unit
): BrowseActions = object : BrowseActions() {
    override fun backStackClick(folder: StorageNavigator.Folder?) {
        folder?.let {
            vm.openDirectory(id = it.id, name = it.name)
        } ?: vm.openRootDirectory()
    }

    override fun open(id: Long) {
        navActions.toExportPrivately(id)
    }

    override fun click(item: Item) {
        val (id, _, name) = item
        when {
            state.mode is Mode.Multiselect && state.multiselectStateList.isNotEmpty() -> {
                onSelectItem(id)
            }
            item.isFolder -> if (state.isStarred) {
                navActions.toFiles(id, name)
            } else {
                if (state.mode === Mode.Move && state.multiselectStateList.contains(id)) return
                vm.openDirectory(id, name)
            }
            else -> open(id)
        }
    }

    override fun longClick(id: Long) {
        if (state.mode !== Mode.Move) onSelectItem(id)
    }
}
