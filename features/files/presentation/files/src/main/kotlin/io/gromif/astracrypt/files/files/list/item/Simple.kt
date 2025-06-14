package io.gromif.astracrypt.files.files.list.item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.shared.iconAlt
import io.gromif.astracrypt.files.shared.iconTint
import io.gromif.astracrypt.files.shared.title
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.theme.spaces

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun FilesListItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    name: String = "TEST_NAME",
    preview: FileSource?,
    itemType: ItemType = ItemType.Document,
    state: ItemState = ItemState.Default,
    isChecked: Boolean = false,
    isBackgroundTransparent: Boolean = false,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    val backgroundColor = animateColorAsState(
        targetValue = if (isChecked) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            Color.Unspecified
        },
        label = ""
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .run {
                if (!isBackgroundTransparent) {
                    background(color = backgroundColor.value)
                } else {
                    this
                }
            }
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .height(dimensionResource(id = R.dimen.filesListItemMediumHeight)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(dimensionResource(id = R.dimen.filesListItemMediumHeight)),
            contentAlignment = Alignment.Center
        ) {
            if (preview == null) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = itemType.iconAlt,
                    contentDescription = null,
                    tint = itemType.iconTint
                )
            } else {
                AsyncImage(
                    model = preview,
                    contentDescription = null,
                    imageLoader = imageLoader,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(85.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            this@Row.AnimatedVisibility(
                visible = state == ItemState.Starred,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    modifier = Modifier
                        .border(
                            width = MaterialTheme.spaces.spaceXXSmall,
                            color = MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
                        .padding(MaterialTheme.spaces.spaceXXSmall)
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
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                text = stringResource(id = itemType.title),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
