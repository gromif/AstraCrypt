package com.nevidimka655.astracrypt.view.composables.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.nevidimka655.astracrypt.view.navigation.BottomBarItems

@Composable
fun BottomBarImpl(
    visible: Boolean,
    selected: BottomBarItems?,
    onTabClick: (BottomBarItems) -> Unit
) {
    val localDensity = LocalDensity.current
    val windowInsets = WindowInsets.systemBars
    AnimatedVisibility(
        visible = visible,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically(targetHeight = {
            windowInsets.getBottom(localDensity)
        }) + fadeOut()
    ) {
        BottomAppBar {
            BottomBarItems.entries.forEach {
                NavigationBarItem(
                    selected = selected == it,
                    onClick = { if (selected != it) onTabClick(it) },
                    icon = {
                        Icon(
                            if (selected == it) it.icon else it.iconOutline,
                            stringResource(id = it.titleId)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = it.titleId),
                            fontWeight = if (selected == it) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
    }
}