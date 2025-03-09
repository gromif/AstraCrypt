package io.gromif.astracrypt.presentation.navigation.lab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.resources.R
import io.gromif.ui.compose.core.theme.spaces

@Preview
@Composable
internal fun LabScreen(
    navigateToAeadEncryption: () -> Unit = {},
    navigateToCombinedZip: () -> Unit = {}
) = LazyVerticalGrid(
    modifier = Modifier.fillMaxSize(),
    columns = GridCells.Adaptive(250.dp),
    contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    item {
        LabScreenListItem(
            imageVector = Icons.Filled.EnhancedEncryption,
            title = stringResource(id = R.string.lab_aeadEncryption),
            clickCallback = navigateToAeadEncryption
        )
    }
    item {
        LabScreenListItem(
            imageVector = Icons.Filled.Archive,
            title = stringResource(id = R.string.lab_combinedZip),
            clickCallback = navigateToCombinedZip
        )
    }
}

@Preview
@Composable
private fun LabScreenListItem(
    imageVector: ImageVector = Icons.Default.Search,
    title: String = "Some title here",
    clickCallback: () -> Unit = {}
) = ElevatedCard(modifier = Modifier.fillMaxWidth()) {
    Row(
        modifier = Modifier
            .clickable(onClick = clickCallback)
            .padding(MaterialTheme.spaces.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Box(modifier = Modifier.size(64.dp)) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                imageVector = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}