package com.nevidimka655.astracrypt.app.theme.icons.file

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.app.theme.icons._FileType

fun _FileType.Builder.fileAlt(background: Color, foreground: Color) = ImageVector.Builder(
    name = "fileAlt",
    defaultWidth = 64.dp,
    defaultHeight = 64.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    // Background Rectangle Path
    path(
        fill = SolidColor(background),
    ) {
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
    path(
        fill = SolidColor(foreground),
    ) {
        moveTo(13f, 8f)
        verticalLineTo(3.5f)
        lineTo(17.5f, 8f)
        close()
    }
}.build()