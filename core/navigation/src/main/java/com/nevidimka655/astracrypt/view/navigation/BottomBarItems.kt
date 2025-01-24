package com.nevidimka655.astracrypt.view.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.resources.R

enum class BottomBarItems(
    @StringRes val titleId: Int,
    val icon: ImageVector,
    val iconOutline: ImageVector,
    val route: Any
) {
    Home(
        titleId = R.string.home,
        icon = Icons.Default.Home,
        iconOutline = Icons.Outlined.Home,
        route = Route.Tabs.Home
    ),
    Files(
        titleId = R.string.files,
        icon = Icons.Default.Folder,
        iconOutline = Icons.Outlined.Folder,
        route = Route.Tabs.Files()
    ),
    Starred(
        titleId = R.string.starred,
        icon = Icons.Default.Star,
        iconOutline = Icons.Outlined.StarOutline,
        route = Route.Tabs.Starred
    ),
    Settings(
        titleId = R.string.settings,
        icon = Icons.Default.Settings,
        iconOutline = Icons.Outlined.Settings,
        route = Route.Tabs.Settings
    )

}