package com.nevidimka655.astracrypt.view.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.data.room.StorageItemState
import com.nevidimka655.astracrypt.data.room.StorageItemType
import com.nevidimka655.astracrypt.domain.room.PagerTuple
import com.nevidimka655.astracrypt.features.profile.model.Avatars
import com.nevidimka655.astracrypt.features.profile.shared.ProfileWidget
import com.nevidimka655.ui.compose_core.CardWithTitle
import com.nevidimka655.ui.compose_core.theme.spaces

@Composable
fun HomeScreen(
    recentFiles: List<PagerTuple>,
    imageLoader: ImageLoader,
    coilAvatarModel: CoilTinkModel?,
    defaultAvatar: Avatars? = null,
    name: String?,
    onOpenRecent: (PagerTuple) -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    ProfileWidget(
        imageLoader = imageLoader,
        name = name,
        coilAvatarModel = coilAvatarModel,
        defaultAvatar = defaultAvatar
    )
    CardWithTitle(
        modifier = Modifier.height(350.dp),
        titleText = stringResource(id = R.string.recentlyAdded)
    ) {
        RecentList(recentFiles = recentFiles, imageLoader = imageLoader) {
            onOpenRecent(it)
        }
    }
}

@Composable
inline fun RecentList(
    recentFiles: List<PagerTuple>,
    imageLoader: ImageLoader,
    crossinline onClick: (item: PagerTuple) -> Unit
) = LazyRow(
    modifier = Modifier.fillMaxSize(),
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    items(
        count = recentFiles.size,
        key = { recentFiles[it].id }
    ) {
        val item = recentFiles[it]
        RecentListItem(
            name = item.name,
            imageLoader = imageLoader,
            preview = item.preview,
            itemType = item.itemType,
            state = item.state
        ) { onClick(item) }
    }
}

@Composable
inline fun RecentListItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    name: String,
    preview: String? = null,
    itemType: StorageItemType,
    state: StorageItemState,
    crossinline onClick: () -> Unit
) = OutlinedCard(
    modifier = modifier
        .fillMaxHeight()
        .width(190.dp)
) {
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
                painter = itemType.iconAlt,
                contentDescription = null,
                tint = itemType.iconTint
            ) else AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = CoilTinkModel(path = preview),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop
            )
            if (state.isStarred) Icon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MaterialTheme.spaces.spaceAltSmall)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = CircleShape
                    )
                    .padding(MaterialTheme.spaces.spaceAltSmall)
                    .size(14.dp),
                painter = rememberVectorPainter(image = Icons.Filled.Star),
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