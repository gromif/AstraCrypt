package io.gromif.astracrypt.presentation.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

val ToolbarActions.close get() = _ToolbarActionClose

object _ToolbarActionClose: ToolbarActions.Action {
    override val contentDescription: Int = R.string.back
    override val icon: ImageVector = Icons.Default.Close
}