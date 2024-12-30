package com.nevidimka655.astracrypt.view.models.actions

import androidx.compose.ui.graphics.vector.ImageVector

object ToolbarActions {

    interface Action {
        val contentDescription: Int
        val icon: ImageVector
    }

}