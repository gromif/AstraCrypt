package io.gromif.astracrypt.presentation.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.astracrypt.resources.R

internal val ToolbarActions.unStar get() = _ToolbarActionUnstar

internal object _ToolbarActionUnstar: ToolbarActions.Action {
    override val contentDescription: Int = R.string.files_options_removeFromStarred
    override val icon: ImageVector = Icons.Outlined.StarOutline
}