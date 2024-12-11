package com.nevidimka655.astracrypt.data.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class ToolbarAction(
    @StringRes val contentDescription: Int,
    val icon: ImageVector
)
