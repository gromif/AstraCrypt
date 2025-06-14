package io.gromif.ui.compose.core

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) = TextButton(
    modifier = modifier,
    enabled = enabled,
    onClick = onClick
) {
    Text(title)
}

@Composable
fun OutlinedButtonWithIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit = {}
) = OutlinedButton(
    modifier = modifier, onClick = onClick
) {
    Icon(icon, null, modifier = Modifier.size(16.dp))
    Spacer(modifier = Modifier.Companion.width(MaterialTheme.spaces.spaceMedium))
    Text(text = title)
}

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) = Button(
    modifier = modifier, onClick = onClick, enabled = enabled
) {
    Icon(icon, null, modifier = Modifier.size(16.dp))
    Spacer(modifier = Modifier.width(MaterialTheme.spaces.spaceMedium))
    Text(text = title)
}

@Composable
fun FilledTonalButtonWithIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) = FilledTonalButton(
    modifier = modifier, onClick = onClick, enabled = enabled
) {
    Icon(icon, null, modifier = Modifier.size(16.dp))
    Spacer(modifier = Modifier.width(MaterialTheme.spaces.spaceMedium))
    Text(text = title)
}

@Composable
fun FilledTonalIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit
) = FilledTonalIconButton(
    modifier = modifier, onClick = onClick, enabled = enabled
) { Icon(icon, contentDescription) }

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit
) = IconButton(
    onClick = onClick, modifier = modifier, enabled = enabled
) { Icon(icon, null) }