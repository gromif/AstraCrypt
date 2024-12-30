package com.nevidimka655.astracrypt.view.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

val ToolbarActions.help get() = _ToolbarActionHelp

object _ToolbarActionHelp: ToolbarActions.Action {
    override val contentDescription: Int = R.string.help
    override val icon: ImageVector = Icons.AutoMirrored.Outlined.HelpOutline
}