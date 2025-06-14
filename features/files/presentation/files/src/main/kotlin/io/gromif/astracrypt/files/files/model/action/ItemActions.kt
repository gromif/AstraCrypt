package io.gromif.astracrypt.files.files.model.action

import androidx.compose.runtime.Immutable

@Immutable
internal open class ItemActions {
    open fun createFolder(name: String) {}
    open fun move() {}
    open fun star(state: Boolean, idList: List<Long>) {}
    open fun rename(id: Long, name: String) {}
    open fun delete(idList: List<Long>) {}
}
