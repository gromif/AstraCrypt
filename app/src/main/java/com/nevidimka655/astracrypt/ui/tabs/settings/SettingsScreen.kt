package com.nevidimka655.astracrypt.ui.tabs.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.theme.spaces

@Composable
fun SettingsScreen(
    onAbout: () -> Unit = {}
) {
    val cellsCount = when(LocalWindowWidth.current) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        WindowWidthSizeClass.Expanded -> 5
        else -> 5
    }
    val list = remember {
        listOf(
            Pair(Icons.Outlined.Person, R.string.settings_editProfile),
            Pair(Icons.Outlined.Security, R.string.settings_security),
            Pair(Icons.Outlined.Palette, R.string.settings_interface),
            Pair(Icons.Outlined.Storefront, R.string.purchases),
            Pair(Icons.Outlined.Analytics, R.string.settings_dataCollection),
            Pair(Icons.Outlined.Info, R.string.settings_about)
        )
    }
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(cellsCount),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
        contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium)
    ) {
        items(list.size) { key ->
            val item = list[key]
            SettingsGroupItem(
                title = stringResource(id = item.second),
                imageVector = item.first
            ) {
                when (key) {
                    /*0 -> navigate(R.id.action_settingsFragment_to_editProfileFragment)
                    1 -> navigate(R.id.action_settingsFragment_to_securityFragment)
                    2 -> navigate(R.id.action_settingsFragment_to_interfaceFragment)
                    3 -> navigate(R.id.action_global_subscriptionsGraph)
                    4 -> navigate(R.id.action_settingsFragment_to_dataCollectionFragment)*/
                    5 -> onAbout()
                }
            }
        }
    }
}

@Composable
inline fun SettingsGroupItem(
    title: String,
    imageVector: ImageVector,
    crossinline onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                },
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .padding(10.dp)
                    .size(32.dp),
                painter = rememberVectorPainter(image = imageVector),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}