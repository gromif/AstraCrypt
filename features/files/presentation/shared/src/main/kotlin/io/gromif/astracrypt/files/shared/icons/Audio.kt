package io.gromif.astracrypt.files.shared.icons

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
import io.gromif.astracrypt.files.shared._FileType
import io.gromif.astracrypt.ui.design_system.darkScheme
import io.gromif.astracrypt.ui.design_system.lightScheme

val _FileType.Audio @Composable get() = default()
val _FileType.AudioAlt @Composable get() = alt()

@Composable
private fun default(): ImageVector = with(MaterialTheme.colorScheme) {
    audio(background = errorContainer, foreground = onErrorContainer)
}

@Composable
private fun alt(): ImageVector = with(MaterialTheme.colorScheme) {
    audio(background = onSurfaceVariant, foreground = surface)
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

@Suppress("detekt:MagicNumber")
private fun audio(background: Color, foreground: Color) = ImageVector.Builder(
    name = "audio",
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
        moveTo(12.1f, 5.9f)
        curveTo(8.9f, 5.9f, 6.3f, 8.5f, 6.3f, 11.7f)
        verticalLineTo(16.2f)
        curveTo(6.3f, 17.2f, 7.2f, 18.1f, 8.2f, 18.1f)
        horizontalLineTo(10.1f)
        verticalLineTo(13f)
        horizontalLineTo(7.5f)
        verticalLineTo(11.7f)
        curveTo(7.5f, 9.2f, 9.5f, 7.2f, 12f, 7.2f)
        curveTo(14.5f, 7.2f, 16.5f, 9.2f, 16.5f, 11.7f)
        verticalLineTo(13f)
        horizontalLineTo(13.9f)
        verticalLineTo(18f)
        horizontalLineTo(15.8f)
        curveTo(16.8f, 18f, 17.7f, 17.1f, 17.7f, 16.1f)
        verticalLineTo(11.6f)
        curveTo(17.7f, 8.4f, 15.1f, 5.9f, 12.1f, 5.9f)
        close()
    }
}.build()
