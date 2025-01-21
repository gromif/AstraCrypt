package io.gromif.astracrypt.files.shared.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import coil.ImageLoader
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.cellsCount
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.shared.list.item.FilesListGridItem
import io.gromif.astracrypt.files.shared.list.item.FilesListItem

@Composable
internal fun FilesList(
    viewMode: ViewMode = ViewMode.Grid,
    pagingItems: LazyPagingItems<FileItem>,
    listCheckedState: SnapshotStateMap<Long, Boolean>,
    imageLoader: ImageLoader,
    onOptions: (item: FileItem) -> Unit,
    onClick: (item: FileItem) -> Unit,
    onLongPress: (item: FileItem) -> Unit,
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
        items(
            count = pagingItems.itemSnapshotList.size,
            key = { pagingItems[it]?.id ?: it }
        ) { index ->
            val item = pagingItems[index] ?: return@items
            when (viewMode) {
                ViewMode.Grid -> FilesListGridItem(
                    modifier = Modifier.animateItem(),
                    imageLoader = imageLoader,
                    name = item.name,
                    preview = item.preview,
                    itemType = item.type,
                    isFile = item.isFile,
                    state = item.state,
                    isChecked = listCheckedState.getOrElse(item.id) { false },
                    onOptions = {
                        Haptic.rise()
                        onOptions.invoke(item)
                    },
                    onClick = { onClick.invoke(item) },
                    onLongPress = {
                        Haptic.clickHeavy()
                        onLongPress.invoke(item)
                    }
                )
                ViewMode.ListDefault -> FilesListItem(
                    modifier = Modifier.animateItem(),
                    imageLoader = imageLoader,
                    name = item.name,
                    preview = item.preview,
                    itemType = item.type,
                    state = item.state,
                    isChecked = listCheckedState.getOrElse(item.id) { false },
                    onLongClick = {
                        Haptic.clickHeavy()
                        onOptions.invoke(item)
                    },
                    onClick = { onClick.invoke(item) }
                )
            }
        }
    }
}