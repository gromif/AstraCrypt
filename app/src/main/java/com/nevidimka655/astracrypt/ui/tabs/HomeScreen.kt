package com.nevidimka655.astracrypt.ui.tabs

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.entities.CoilTinkModel
import com.nevidimka655.astracrypt.features.profile.ui.ProfileWidget
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.enums.StorageItemState
import com.nevidimka655.astracrypt.utils.enums.StorageItemType
import com.nevidimka655.ui.compose_core.CardWithTitle
import com.nevidimka655.ui.compose_core.theme.spaces

@Composable
fun HomeScreen(
    vm: MainVM,
    onOpenFile: (Long) -> Unit,
    openFolder: () -> Unit
) {
    SideEffect { vm.loadProfileInfo() }
    val profileInfoState = vm.profileInfoFlow.collectAsStateWithLifecycle()
    val recentItemsState = vm.recentFilesStateFlow.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection())
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spaces.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        ProfileWidget(profileInfoState = profileInfoState)
        CardWithTitle(
            modifier = Modifier.height(350.dp),
            titleText = stringResource(id = R.string.recentlyAdded)
        ) {
            RecentList(itemsState = recentItemsState) {
                if (it.isFile) onOpenFile(it.id) else {
                    vm.openDirectory(
                        id = it.id,
                        dirName = it.name,
                        popBackStack = true
                    )
                    vm.triggerFilesListUpdate()
                    openFolder()
                }
            }
        }
    }
}

@Composable
inline fun RecentList(
    itemsState: State<List<StorageItemListTuple>>,
    crossinline onClick: (item: StorageItemListTuple) -> Unit
) = LazyRow(
    modifier = Modifier.fillMaxSize(),
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    items(
        count = itemsState.value.size,
        key = { itemsState.value[it].id }
    ) {
        val item = itemsState.value[it]
        RecentListItem(
            name = item.name,
            thumb = item.thumbnail,
            thumbEncryption = item.thumbnailEncryptionType,
            itemType = item.itemType,
            state = item.state
        ) { onClick(item) }
    }
}

@Composable
inline fun RecentListItem(
    modifier: Modifier = Modifier,
    name: String,
    thumb: String,
    thumbEncryption: Int,
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
            if (thumb.isEmpty()) {
                if (itemType == StorageItemType.Folder) Icon(
                    modifier = Modifier.size(72.dp),
                    imageVector = itemType.iconAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                ) else Image(
                    modifier = Modifier.size(72.dp),
                    imageVector = itemType.iconAlt,
                    contentDescription = null
                )
            } else AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = CoilTinkModel(path = thumb, encryptionType = thumbEncryption),
                contentDescription = null,
                imageLoader = Engine.imageLoader,
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