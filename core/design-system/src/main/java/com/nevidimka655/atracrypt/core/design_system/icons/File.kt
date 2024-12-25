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

val _FileType.File @Composable get() = default()
val _FileType.FileAlt @Composable get() = alt()

@Composable
private fun default(): ImageVector = with(MaterialTheme.extendedColorScheme.blue) {
    file(background = colorContainer, foreground = onColorContainer)
}

@Composable
private fun alt(): ImageVector = with(MaterialTheme.colorScheme) {
    file(background = onSurfaceVariant, foreground = surface)
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

private fun file(background: Color, foreground: Color) = ImageVector.Builder(
    name = "file",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    // Background Rectangle Path
    path(fill = SolidColor(background)) {
        moveTo(14f, 2f)
        horizontalLineTo(6f)
        curveTo(4.9f, 2f, 4f, 2.9f, 4f, 4f)
        verticalLineTo(20f)
        curveTo(4f, 21.1f, 4.9f, 22f, 6f, 22f)
        horizontalLineTo(18f)
        curveTo(19.1f, 22f, 20f, 21.1f, 20f, 20f)
        verticalLineTo(8f)
        lineTo(14f, 2f)
        close()
    }
    // Foreground Shape Path
    path(fill = SolidColor(foreground)) {
        moveTo(13f, 8f)
        verticalLineTo(3.5f)
        lineTo(17.5f, 8f)
        close()
    }
}.build()