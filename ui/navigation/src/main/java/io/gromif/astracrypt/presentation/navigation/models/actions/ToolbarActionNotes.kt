package io.gromif.astracrypt.presentation.navigation.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.astracrypt.resources.R

val ToolbarActions.notes get() = _ToolbarActionNotes

object _ToolbarActionNotes : ToolbarActions.Action {
    override val contentDescription: Int = R.string.notes
    override val icon: ImageVector = Icons.AutoMirrored.Default.Notes
}
