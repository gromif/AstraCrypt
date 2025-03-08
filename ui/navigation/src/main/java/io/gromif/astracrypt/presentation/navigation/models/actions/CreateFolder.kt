package io.gromif.astracrypt.presentation.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

internal val ToolbarActions.createFolder get() = _ToolbarActionCreateFolder

internal object _ToolbarActionCreateFolder: ToolbarActions.Action {
    override val contentDescription: Int = R.string.createNew
    override val icon: ImageVector = Icons.Outlined.CreateNewFolder
}