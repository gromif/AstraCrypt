package com.nevidimka655.astracrypt.entities

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class ActionItem(
    @StringRes val contentDescription: Int,
    val icon: ImageVector
)
