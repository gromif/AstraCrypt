package io.gromif.astracrypt.files.files.model.action

import android.net.Uri
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.StorageNavigator

internal open class Actions {

    open fun backStackClick(folder: StorageNavigator.Folder?) {}
    open fun click(item: Item) {}
    open fun longClick(id: Long) {}

    open fun setMoveMode() {}
    open fun closeContextualToolbar() {}

    open fun open(id: Long) {}
    open fun createFolder(name: String) {}
    open fun import(uriList: Array<Uri>, saveSource: Boolean) {}
    open fun scan() {}
    open fun move() {}
    open fun star(state: Boolean, idList: List<Long>) {}
    open fun rename(id: Long, name: String) {}
    open fun delete(idList: List<Long>) {}
}
