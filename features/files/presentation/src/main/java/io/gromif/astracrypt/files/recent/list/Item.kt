package io.gromif.astracrypt.files.recent.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.shared.iconAlt
import io.gromif.astracrypt.files.shared.iconTint
import io.gromif.ui.compose.core.theme.spaces

@Composable
internal fun RecentFilesListItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    name: String,
    preview: FileSource?,
    itemType: ItemType,
    state: ItemState,
    onClick: () -> Unit
) = Card(modifier = modifier.fillMaxHeight()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
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
                contentScale = ContentScale.Crop
            )
            if (state == ItemState.Starred) Icon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
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
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = MaterialTheme.spaces.spaceAltSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        }
    }
}