package io.gromif.astracrypt.presentation.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.astracrypt.resources.R

val ToolbarActions.delete get() = _ToolbarActionDelete

object _ToolbarActionDelete : ToolbarActions.Action {
    override val contentDescription: Int = R.string.files_options_delete
    override val icon: ImageVector = Icons.Outlined.DeleteOutline
}
