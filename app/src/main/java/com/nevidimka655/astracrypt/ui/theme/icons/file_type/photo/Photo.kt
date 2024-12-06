package com.nevidimka655.astracrypt.ui.theme.icons.file_type.photo

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.nevidimka655.astracrypt.ui.theme.darkScheme
import com.nevidimka655.astracrypt.ui.theme.extendedColorScheme
import com.nevidimka655.astracrypt.ui.theme.icons.file_type._FileType
import com.nevidimka655.astracrypt.ui.theme.lightScheme

val _FileType.Photo @Composable get() = icon()

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
    val colorScheme = MaterialTheme.extendedColorScheme.green
    val background = colorScheme.colorContainer
    val foreground = colorScheme.onColorContainer
    return rememberVectorPainter(
        _FileType.Builder.photoAlt(
            background = background,
            foreground = foreground
        )
    )
}