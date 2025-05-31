package io.gromif.astracrypt.files.recent.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.shared.FakeData
import io.gromif.ui.compose.core.theme.spaces

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
internal fun RecentFilesList(
    list: List<Item> = FakeData.fileItems(),
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    actions: Actions = Actions.default,
) = HorizontalMultiBrowseCarousel(
    state = rememberCarouselState { list.size },
    preferredItemWidth = 180.dp,
    itemSpacing = MaterialTheme.spaces.spaceMedium,
    flingBehavior = CarouselDefaults.noSnapFlingBehavior(),
    modifier = Modifier.fillMaxWidth().height(300.dp)
) {
    val item = list[it]
    RecentFilesListItem(
        modifier = Modifier.maskClip(MaterialTheme.shapes.medium),
        name = item.name,
        imageLoader = imageLoader,
        preview = item.preview,
        itemType = item.type,
        state = item.state
    ) {
        if (item.isFolder) {
            actions.openFolder(
                id = item.id,
                name = item.name
            )
        } else {
            actions.openFile(id = item.id)
        }
    }
}
