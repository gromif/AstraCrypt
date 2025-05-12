package io.gromif.ui.compose.core.chips

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Chips.Components.icon(icon: ImageVector?): (@Composable () -> Unit)? = if (icon != null) {
    { Icon(imageVector = icon, contentDescription = null) }
} else null