package io.gromif.astracrypt.presentation.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

val ToolbarActions.update_database get() = _ToolbarActionUpdateDatabase

object _ToolbarActionUpdateDatabase: ToolbarActions.Action {
    override val contentDescription: Int = R.string.dialog_dbUpdate
    override val icon: ImageVector = Icons.Outlined.Autorenew
}