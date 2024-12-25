package com.nevidimka655.atracrypt.core.design_system.icons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.nevidimka655.atracrypt.core.design_system.darkScheme
import com.nevidimka655.atracrypt.core.design_system.extendedColorScheme
import com.nevidimka655.atracrypt.core.design_system.lightScheme

val _FileType.Photo @Composable get() = default()
val _FileType.PhotoAlt @Composable get() = alt()

@Composable
private fun default(): ImageVector = with(MaterialTheme.extendedColorScheme.green) {
    photo(background = colorContainer, foreground = onColorContainer)
}

@Composable
private fun alt(): ImageVector = with(MaterialTheme.colorScheme) {
    photo(background = onSurfaceVariant, foreground = surface)
}

@Preview
@Composable
private fun Preview(vectors: List<ImageVector> = listOf(default(), alt())) = Column {
    vectors.forEach { vector ->
        Row {
            listOf(lightScheme, darkScheme).fastForEach {
                MaterialTheme(colorScheme = it) {
                    Surface { Icon(vector, null, Modifier.size(128.dp), Color.Unspecified) }
                }
            }
        }
    }
}

private fun photo(background: Color, foreground: Color) = ImageVector.Builder(
    name = "photo",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    // Background Rectangle Path
    path(fill = SolidColor(background)) {
        moveTo(21f, 19f)
        verticalLineTo(5f)
        curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
        horizontalLineTo(5f)
        curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
        verticalLineTo(19f)
        curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
        horizontalLineTo(19f)
        curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
        close()
    }
    // Foreground Shape Path
    path(fill = SolidColor(foreground)) {
        moveTo(8.5f, 13.5f)
        lineTo(11f, 16.5f)
        lineTo(14.5f, 12f)
        lineTo(19f, 18f)
        lineTo(5f, 18f)
        close()
    }
}.build()