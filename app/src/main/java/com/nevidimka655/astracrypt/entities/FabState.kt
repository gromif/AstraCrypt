package com.nevidimka655.astracrypt.entities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.graphics.vector.ImageVector

data class FabState(
    val isVisible: Boolean = true,
    val imageVector: ImageVector,
    val contentDescription: String
) {
    companion object {
        val NO = FabState(
            isVisible = false,
            imageVector = Icons.Default.Add,
            contentDescription = ""
        )
    }
}
