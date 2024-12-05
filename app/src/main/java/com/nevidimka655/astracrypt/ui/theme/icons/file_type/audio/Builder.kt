package com.nevidimka655.astracrypt.ui.theme.icons.file_type.audio

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.ui.theme.icons.file_type._FileType

fun _FileType.Builder.audioAlt(background: Color, foreground: Color) = ImageVector.Builder(
    name = "audioAlt",
    defaultWidth = 64.dp,
    defaultHeight = 64.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    // Background Rectangle Path
    path(
        fill = SolidColor(background),
    ) {
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
    path(
        fill = SolidColor(foreground),
    ) {
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