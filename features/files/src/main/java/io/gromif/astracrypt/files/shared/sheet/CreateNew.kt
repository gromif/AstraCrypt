package io.gromif.astracrypt.files.shared.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.IconWithBorder
import com.nevidimka655.ui.compose_core.sheets.SheetDefaults
import com.nevidimka655.ui.compose_core.sheets.default
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun filesCreateNewSheet(
    state: MutableState<Boolean>,
    sheetState: SheetState = SheetDefaults.state(),
    scope: CoroutineScope = rememberCoroutineScope(),
    onCreateFolder: () -> Unit,
    onAdd: (type: String) -> Unit,
    onScan: () -> Unit,
) = SheetDefaults.default(
    state = state,
    sheetState = sheetState,
    title = stringResource(id = R.string.createNew)
) {
    @Composable
    fun GridItem(
        imageVector: ImageVector,
        text: String,
        onClick: () -> Unit
    ) = Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .clickable {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        state.value = false
                        onClick()
                    }
                },
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spaces.spaceMedium, Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconWithBorder(imageVector = imageVector)
            Text(text = text, style = MaterialTheme.typography.bodySmall)
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.spaces.spaceMedium, Alignment.CenterVertically
        ),
        horizontalArrangement = Arrangement.Center
    ) {
        item {
            GridItem(
                imageVector = Icons.Outlined.FolderOpen,
                text = stringResource(id = R.string.folder),
                onClick = onCreateFolder
            )
        }
        item {
            GridItem(
                imageVector = Icons.Outlined.Add,
                text = stringResource(id = R.string.add),
                onClick = { onAdd("*") }
            )
        }
        item {
            GridItem(
                imageVector = Icons.Outlined.Camera,
                text = stringResource(id = R.string.scan),
                onClick = onScan
            )
        }
        item {
            GridItem(
                imageVector = Icons.Outlined.Photo,
                text = stringResource(id = R.string.photo),
                onClick = { onAdd("image") }
            )
        }
        item {
            GridItem(
                imageVector = Icons.Outlined.Videocam,
                text = stringResource(id = R.string.video),
                onClick = { onAdd("video") }
            )
        }
        item {
            GridItem(
                imageVector = Icons.Outlined.MusicNote,
                text = stringResource(id = R.string.music),
                onClick = { onAdd("audio") }
            )
        }
    }
}