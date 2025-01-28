package io.gromif.astracrypt.files.shared.recent.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.shared.FakeData

@Preview(showBackground = true)
@Composable
internal fun RecentFilesList(
    list: List<FileItem> = FakeData.fileItems(),
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    actions: Actions = Actions.default,
) = LazyRow(
    modifier = Modifier.height(350.dp).fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    items(
        count = list.size,
        key = { list[it].id }
    ) {
        val item = list[it]
        RecentFilesListItem(
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
}