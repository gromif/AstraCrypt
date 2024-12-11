package com.nevidimka655.astracrypt.app.theme.icons.photo

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.app.theme.darkScheme
import com.nevidimka655.astracrypt.app.theme.icons._FileType
import com.nevidimka655.astracrypt.app.theme.lightScheme

val _FileType.PhotoAlt @Composable get() = icon()

@Preview
@Composable
private fun Preview() = MaterialTheme(colorScheme = lightScheme) {
    Surface { Icon(painter = icon(), contentDescription = null, tint = Color.Unspecified) }
}

@Preview
@Composable
private fun PreviewDark() = MaterialTheme(colorScheme = darkScheme) {
    Surface { Icon(painter = icon(), contentDescription = null, tint = Color.Unspecified) }
}

@Composable
private fun icon(): VectorPainter {
    val colorScheme = MaterialTheme.colorScheme
    val colorOnSurfaceVariant = colorScheme.onSurfaceVariant
    val colorSurface = colorScheme.surface
    return rememberVectorPainter(
        _FileType.Builder.photoAlt(
            background = colorOnSurfaceVariant,
            foreground = colorSurface
        )
    )
}