package com.nevidimka655.astracrypt.app.theme.icons.video

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.app.theme.icons._FileType

fun _FileType.Builder.videoAlt(foreground: Color) = ImageVector.Builder(
    name = "videoAlt",
    defaultWidth = 64.dp,
    defaultHeight = 64.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        fill = SolidColor(foreground),
    ) {
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