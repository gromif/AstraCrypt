package com.nevidimka655.astracrypt.view.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

internal val ToolbarActions.star get() = _ToolbarActionStar

internal object _ToolbarActionStar: ToolbarActions.Action {
    override val contentDescription: Int = R.string.files_options_addToStarred
    override val icon: ImageVector = Icons.Default.Star
}