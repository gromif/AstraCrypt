package com.nevidimka655.astracrypt.ui.theme.icons.file_type.audio

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.ui.theme.ColorSchemes
import com.nevidimka655.astracrypt.ui.theme.icons.file_type._FileType

val _FileType.Audio @Composable get() = icon()

@Preview
@Composable
private fun Preview() = MaterialTheme(colorScheme = ColorSchemes.LightColors) {
    Surface { Icon(painter = icon(), contentDescription = null, tint = Color.Unspecified) }
}

@Preview
@Composable
private fun PreviewDark() = MaterialTheme(colorScheme = ColorSchemes.DarkColors) {
    Surface { Icon(painter = icon(), contentDescription = null, tint = Color.Unspecified) }
}

@Composable
private fun icon(): VectorPainter {
    val colorScheme = MaterialTheme.colorScheme
    val colorOnSurfaceVariant = colorScheme.onSurfaceVariant
    val colorSurface = colorScheme.surface
    return rememberVectorPainter(
        _FileType.Builder.audioAlt(
            background = colorOnSurfaceVariant,
            foreground = colorSurface
        )
    )
}