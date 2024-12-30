package com.nevidimka655.astracrypt.view.navigation.models.actions

import androidx.compose.ui.graphics.vector.ImageVector

object ToolbarActions {

    interface Action {
        val contentDescription: Int
        val icon: ImageVector
    }

}