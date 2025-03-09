package io.gromif.astracrypt.presentation.navigation.tabs.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.gromif.astracrypt.presentation.navigation.Route
import io.gromif.astracrypt.presentation.navigation.shared.LocalNavController
import io.gromif.ui.compose.core.ext.LocalWindowWidth
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun SettingsScreen() {
    val navController = LocalNavController.current

    val cellsCount = when(LocalWindowWidth.current) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        WindowWidthSizeClass.Expanded -> 5
        else -> 5
    }
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(cellsCount),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
        contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium)
    ) {
        items(SettingsMainItems.entries) { key ->
            SettingsGroupItem(
                title = stringResource(id = key.titleId),
                imageVector = key.imageVector
            ) {
                when (key) {
                    SettingsMainItems.EditProfile -> navController.navigate(Route.EditProfile)
                    SettingsMainItems.Security -> navController.navigate(Route.SettingsSecurity)
                    SettingsMainItems.Interface -> navController.navigate(Route.SettingsUi)
                    SettingsMainItems.About -> navController.navigate(Route.AboutGraph)
                }
            }
        }
    }
}

@Composable
fun SettingsGroupItem(
    title: String,
    imageVector: ImageVector,
    onClick: () -> Unit
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
                imageVector = imageVector,
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