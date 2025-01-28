package io.gromif.astracrypt.files.shared.recent.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.shared.FakeData

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
internal fun RecentFilesList(
    list: List<FileItem> = FakeData.fileItems(),
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    actions: Actions = Actions.default,
) = HorizontalUncontainedCarousel(
    state = rememberCarouselState { list.size },
    itemWidth = 186.dp,
    itemSpacing = MaterialTheme.spaces.spaceMedium,
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
        if (item.isFolder) actions.openFolder(
            id = item.id,
            name = item.name
        ) else actions.openFile(id = item.id)
    }
}