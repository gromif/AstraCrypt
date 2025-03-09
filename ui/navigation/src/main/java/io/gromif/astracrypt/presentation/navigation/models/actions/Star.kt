package io.gromif.astracrypt.presentation.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.astracrypt.resources.R

internal val ToolbarActions.star get() = _ToolbarActionStar

internal object _ToolbarActionStar: ToolbarActions.Action {
    override val contentDescription: Int = R.string.files_options_addToStarred
    override val icon: ImageVector = Icons.Default.Star
}