package io.gromif.astracrypt.files.shared.list.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.shared.icon
import io.gromif.astracrypt.files.shared.iconAlt
import io.gromif.astracrypt.files.shared.iconTint

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FilesListGridItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    name: String,
    preview: FileSource?,
    itemType: ItemType,
    isFile: Boolean,
    state: ItemState,
    isChecked: Boolean,
    onOptions: () -> Unit,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
) = ElevatedCard(
    modifier = modifier.aspectRatio(1f),
    colors = if (isChecked) CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
    ) else CardDefaults.elevatedCardColors(),
    shape = ShapeDefaults.Large
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongPress() }
            )
    ) {
        var loadOtherIcons by remember { mutableStateOf(preview == null) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            contentAlignment = Alignment.Center
        ) {
            if (preview == null) Icon(
                modifier = Modifier.size(72.dp),
                imageVector = itemType.iconAlt,
                contentDescription = null,
                tint = itemType.iconTint
            ) else AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = preview,
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop,
                onState = {
                    loadOtherIcons = it is AsyncImagePainter.State.Success
                })
            this@Column.AnimatedVisibility(
                visible = state == ItemState.Starred,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(MaterialTheme.spaces.spaceAltSmall)
                        .background(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = CircleShape
                        )
                        .padding(MaterialTheme.spaces.spaceAltSmall)
                        .size(14.dp),
                    imageVector = Icons.Filled.Star,
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = null
                )
            }
        }
        if (loadOtherIcons) Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isFile) Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = itemType.icon,
                    contentDescription = null,
                    tint = itemType.iconTint
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = name,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.CheckCircle,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                ) else IconButton(
                    modifier = Modifier.fillMaxSize(),
                    onClick = onOptions
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Filled.MoreVert,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null
                    )
                }
            }
        }
    }
}