package com.nevidimka655.astracrypt.view.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Science
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

val ToolbarActions.lab get() = _ToolbarActionLab

object _ToolbarActionLab: ToolbarActions.Action {
    override val contentDescription: Int = R.string.lab
    override val icon: ImageVector = Icons.Outlined.Science
}