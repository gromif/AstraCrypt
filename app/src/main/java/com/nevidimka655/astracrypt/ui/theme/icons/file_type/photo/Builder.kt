package com.nevidimka655.astracrypt.ui.theme.icons.file_type.photo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.ui.theme.icons.file_type._FileType

fun _FileType.Builder.photoAlt(background: Color, foreground: Color) = ImageVector.Builder(
    name = "photoAlt",
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
        moveTo(8.5f, 13.5f)
        lineTo(11f, 16.5f)
        lineTo(14.5f, 12f)
        lineTo(19f, 18f)
        lineTo(5f, 18f)
        close()
    }
}.build()