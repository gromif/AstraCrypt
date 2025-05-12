package io.gromif.ui.compose.core.banners

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun Banner.Warning(
    text: String = "SIMPLE_TEXT_TOOLTIP",
    containerColor: Color = MaterialTheme.colorScheme.errorContainer,
    iconTintColor: Color = MaterialTheme.colorScheme.onErrorContainer
) = Banner.Note(
    imageVector = Icons.Default.Warning,
    text = text,
    containerColor = containerColor,
    iconTintColor = iconTintColor
)