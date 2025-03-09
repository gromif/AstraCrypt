package io.gromif.astracrypt.files.files.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import coil.ImageLoader
import com.nevidimka655.haptic.Haptic
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.files.list.item.FilesListGridItem
import io.gromif.astracrypt.files.files.list.item.FilesListItem
import io.gromif.ui.compose.core.ext.LocalWindowWidth
import io.gromif.ui.compose.core.ext.cellsCount
import io.gromif.ui.compose.core.theme.spaces

@Composable
internal fun FilesList(
    viewMode: ViewMode = ViewMode.Grid,
    pagingItems: LazyPagingItems<Item>,
    multiselectStateList: SnapshotStateList<Long>,
    imageLoader: ImageLoader,
    onOptions: (item: Item) -> Unit,
    onClick: (item: Item) -> Unit,
    onLongPress: (id: Long) -> Unit,
) {
    val cells = when (viewMode) {
        ViewMode.Grid -> LocalWindowWidth.current.cellsCount(2, 3, 5)
        ViewMode.ListDefault -> LocalWindowWidth.current.cellsCount()
    }
    val contentPadding = PaddingValues(
        when (viewMode) {
            ViewMode.Grid -> MaterialTheme.spaces.spaceSmall
            ViewMode.ListDefault -> MaterialTheme.spaces.none
        }
    )
    val arrangement = Arrangement.spacedBy(
        when (viewMode) {
            ViewMode.Grid -> MaterialTheme.spaces.spaceSmall
            ViewMode.ListDefault -> MaterialTheme.spaces.none
        }
    )
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(cells),
        contentPadding = contentPadding,
        verticalArrangement = arrangement,
        horizontalArrangement = arrangement
    ) {
        when (viewMode) {
            ViewMode.Grid -> items(
                count = pagingItems.itemSnapshotList.size,
                key = { pagingItems[it]?.id ?: it }
            ) { index ->
                val item = pagingItems[index] ?: return@items
                val isItemSelected = multiselectStateList.contains(item.id)
                FilesListGridItem(
                    modifier = Modifier.animateItem(),
                    imageLoader = imageLoader,
                    name = item.name,
                    preview = item.preview,
                    itemType = item.type,
                    isFile = item.isFile,
                    state = item.state,
                    isChecked = isItemSelected,
                    onOptions = {
                        Haptic.rise()
                        onOptions(item)
                    },
                    onClick = { onClick.invoke(item) },
                    onLongPress = {
                        Haptic.clickHeavy()
                        onLongPress(item.id)
                    })
            }
            ViewMode.ListDefault -> items(
                count = pagingItems.itemSnapshotList.size,
                key = { pagingItems[it]?.id ?: it }
            ) { index ->
                val item = pagingItems[index] ?: return@items
                val isItemSelected = multiselectStateList.contains(item.id)
                FilesListItem(
                    modifier = Modifier.animateItem(),
                    imageLoader = imageLoader,
                    name = item.name,
                    preview = item.preview,
                    itemType = item.type,
                    state = item.state,
                    isChecked = isItemSelected,
                    onLongClick = {
                        Haptic.clickHeavy()
                        onOptions(item)
                    },
                    onClick = { onClick.invoke(item) })
            }
        }
    }
}