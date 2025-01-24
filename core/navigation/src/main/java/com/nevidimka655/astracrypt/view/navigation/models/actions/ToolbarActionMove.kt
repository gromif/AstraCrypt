package com.nevidimka655.astracrypt.view.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DriveFileMove
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

val ToolbarActions.move get() = _ToolbarActionMove

object _ToolbarActionMove: ToolbarActions.Action {
    override val contentDescription: Int = R.string.files_options_move
    override val icon: ImageVector = Icons.AutoMirrored.Outlined.DriveFileMove
}