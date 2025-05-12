package io.gromif.ui.compose.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun IconWithBorder(
    size: Dp = 24.dp,
    imageVector: ImageVector = Icons.Outlined.Home,
    borderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    borderShape: Shape = CircleShape,
    tint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) = Icon(
    modifier = Modifier
        .border(width = 1.dp, color = borderColor, shape = borderShape)
        .padding(10.dp)
        .size(size),
    imageVector = imageVector,
    contentDescription = null,
    tint = tint
)

@Preview(showBackground = true)
@Composable
fun ColoredIcon(
    size: Dp = 24.dp,
    imageVector: ImageVector = Icons.Outlined.Home,
    background: Color = MaterialTheme.colorScheme.primaryContainer,
    tint: Color = MaterialTheme.colorScheme.primary,
) = Icon(
    modifier = Modifier
        .background(
            color = background,
            shape = CircleShape
        )
        .padding(10.dp)
        .size(size),
    imageVector = imageVector,
    contentDescription = null,
    tint = tint
)

@Preview(showBackground = true)
@Composable
fun ColoredIconSecondary(
    size: Dp = 24.dp,
    imageVector: ImageVector = Icons.Outlined.Home
) = ColoredIcon(
    size = size,
    imageVector = imageVector,
    background = MaterialTheme.colorScheme.secondaryContainer,
    tint = MaterialTheme.colorScheme.secondary
)

@Preview(showBackground = true)
@Composable
fun ColoredIconTertiary(
    size: Dp = 24.dp,
    imageVector: ImageVector = Icons.Outlined.Home
) = ColoredIcon(
    size = size,
    imageVector = imageVector,
    background = MaterialTheme.colorScheme.tertiaryContainer,
    tint = MaterialTheme.colorScheme.tertiary
)