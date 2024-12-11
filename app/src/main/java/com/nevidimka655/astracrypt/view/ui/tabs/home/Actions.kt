package com.nevidimka655.astracrypt.view.ui.tabs.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.outlined.Science
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.data.model.ToolbarAction
import com.nevidimka655.astracrypt.view.Actions

fun Actions.Screen.homeActions() = listOf(
    ToolbarAction(
        icon = Icons.AutoMirrored.Default.Notes,
        contentDescription = R.string.notes
    ),
    ToolbarAction(
        icon = Icons.Outlined.Science,
        contentDescription = R.string.lab
    ),
)