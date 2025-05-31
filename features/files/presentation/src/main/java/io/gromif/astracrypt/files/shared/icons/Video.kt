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
import io.gromif.astracrypt.ui.design_system.extendedColorScheme
import io.gromif.astracrypt.ui.design_system.lightScheme

internal val _FileType.Video @Composable get() = default()
internal val _FileType.VideoAlt @Composable get() = alt()

@Composable
private fun default(): ImageVector = with(MaterialTheme.extendedColorScheme.violet) {
    video(foreground = onColorContainer)
}

@Composable
private fun alt(): ImageVector = with(MaterialTheme.colorScheme) {
    video(foreground = onSurfaceVariant)
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

private fun video(foreground: Color) = ImageVector.Builder(
    name = "video",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(fill = SolidColor(foreground)) {
        moveTo(18f, 4f)
        lineTo(20f, 8f)
        lineTo(17f, 8f)
        lineTo(15f, 4f)
        lineTo(13f, 4f)
        lineTo(15f, 8f)
        lineTo(12f, 8f)
        lineTo(10f, 4f)
        lineTo(8f, 4f)
        lineTo(10f, 8f)
        lineTo(7f, 8f)
        lineTo(5f, 4f)
        lineTo(4f, 4f)
        curveTo(2.9f, 4f, 2.01f, 4.9f, 2.01f, 6f)
        lineTo(2f, 18f)
        curveTo(2f, 19.1f, 2.9f, 20f, 4f, 20f)
        horizontalLineTo(20f)
        curveTo(21.1f, 20f, 22f, 19.1f, 22f, 18f)
        verticalLineTo(4f)
        horizontalLineTo(18f)
        close()
    }
}.build()
