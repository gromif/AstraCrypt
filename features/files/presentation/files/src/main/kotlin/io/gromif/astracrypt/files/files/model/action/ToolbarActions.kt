package io.gromif.astracrypt.files.files.model.action

import androidx.compose.runtime.Immutable

@Immutable
internal open class ToolbarActions {
    open fun setMoveMode() {}
    open fun closeContextualToolbar() {}
}
