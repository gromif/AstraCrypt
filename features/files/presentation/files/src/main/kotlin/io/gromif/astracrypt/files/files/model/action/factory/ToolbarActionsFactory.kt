package io.gromif.astracrypt.files.files.model.action.factory

import io.gromif.astracrypt.files.files.model.Mode
import io.gromif.astracrypt.files.files.model.action.Actions
import io.gromif.astracrypt.files.files.model.action.ToolbarActions

internal fun Actions.createToolbarActions(
    onCloseContextualToolbar: () -> Unit,
    onModeChange: (Mode) -> Unit
): ToolbarActions = object : ToolbarActions() {
    override fun closeContextualToolbar() = onCloseContextualToolbar()

    override fun setMoveMode() = onModeChange(Mode.Move)
}
