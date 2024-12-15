package com.nevidimka655.astracrypt.view.models.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Science
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.data.model.ToolbarAction

class ToolbarActionLab(
    override val contentDescription: Int = R.string.lab,
    override val icon: ImageVector = Icons.Outlined.Science
) : ToolbarAction