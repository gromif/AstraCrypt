package io.gromif.ui.compose.core.chips

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.ui.haptic.Haptic

object Chips {

    object Components

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun Chips.Filter(
    selected: Boolean,
    crossinline onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null
) = FilterChip(
    selected = selected,
    onClick = {
        Haptic.tick()
        onClick()
    },
    label = { Text(text = label) },
    modifier = modifier, enabled = enabled,
    leadingIcon = Chips.Components.icon(icon = leadingIcon),
    trailingIcon = Chips.Components.icon(icon = trailingIcon)
)