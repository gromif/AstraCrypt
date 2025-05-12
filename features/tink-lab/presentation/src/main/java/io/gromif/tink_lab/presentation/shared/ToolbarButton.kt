package io.gromif.tink_lab.presentation.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.ui.compose.core.FilledTonalButtonWithIcon

@Preview
@Composable
internal fun ToolbarButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Default.Save,
    text: String = "Button",
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) = FilledTonalButtonWithIcon(
    modifier = modifier, enabled = enabled, icon = imageVector, title = text, onClick = onClick
)