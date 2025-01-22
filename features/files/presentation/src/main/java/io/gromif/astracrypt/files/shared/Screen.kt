package io.gromif.astracrypt.files.shared

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import com.nevidimka655.ui.compose_core.Compose
import io.gromif.astracrypt.files.contracts.exportContract
import io.gromif.astracrypt.files.contracts.pickFileContract
import io.gromif.astracrypt.files.dialogs.deleteDialog
import io.gromif.astracrypt.files.dialogs.deleteSourceDialog
import io.gromif.astracrypt.files.dialogs.newFolderDialog
import io.gromif.astracrypt.files.dialogs.renameDialog
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.model.Option
import io.gromif.astracrypt.files.model.OptionsItem
import io.gromif.astracrypt.files.model.RootInfo
import io.gromif.astracrypt.files.shared.list.EmptyList
import io.gromif.astracrypt.files.shared.list.FilesList
import io.gromif.astracrypt.files.shared.sheet.filesCreateNewSheet
import io.gromif.astracrypt.files.shared.sheet.filesOptionsSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
internal fun Screen(
    isStarred: Boolean = false,
    isSearching: Boolean = false,
    viewMode: ViewMode = ViewMode.Grid,
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),

    backStackList: List<RootInfo> = listOf(
        RootInfo(name = "Root1"), RootInfo(name = "Root2"), RootInfo(name = "Root3")
    ),
    onBackStackClick: (index: Int?) -> Unit = {},
    pagingFlow: Flow<PagingData<FileItem>> = pagingFakeData(),

    onClick: (FileItem) -> Unit = {},
    onImport: (List<Uri>, Boolean) -> Unit = { _, _ -> },
    onScan: () -> Unit = {},
    onOpen: () -> Unit = {},
    onCreateFolder: (String) -> Unit = {},
    onStar: (id: Long, state: Boolean) -> Unit = { _, _ -> },
    onRename: (id: Long, name: String) -> Unit = { _, _ -> },
    onDelete: (Long) -> Unit = {},

    sheetCreateState: MutableState<Boolean> = mutableStateOf(false),

    toExport: (id: Long, output: Uri) -> Unit = { _, _ -> },
    toDetails: (id: Long) -> Unit = {},
) = Column {
    val sheetOptionsState = Compose.state()
    var optionsItem by rememberSaveable { mutableStateOf(OptionsItem()) }
    val items = pagingFlow.collectAsLazyPagingItems()

    if (!isSearching) AnimatedVisibility(visible = backStackList.isNotEmpty()) {
        FilesBackStackList(rootBackStack = backStackList, onClick = onBackStackClick)
    }
    val isEmpty = remember {
        derivedStateOf { items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading }
    }
    AnimatedVisibility(visible = !isEmpty.value, enter = fadeIn(), exit = ExitTransition.None) {
        FilesList(
            viewMode = viewMode,
            pagingItems = items,
            listCheckedState = mutableStateMapOf()/*vm.selectorManager.itemsMapState*/,
            imageLoader = imageLoader,
            onOptions = {
                optionsItem = OptionsItem(
                    id = it.id,
                    name = it.name,
                    isStarred = it.state == FileState.Starred,
                    fileType = it.type,
                    isFolder = it.isFolder
                )
                sheetOptionsState.value = true
            },
            onClick = onClick,
            onLongPress = {}
        )
    }
    AnimatedVisibility(visible = isEmpty.value, enter = fadeIn(), exit = ExitTransition.None) {
        EmptyList(isStarredScreen = isStarred, isSearching = isSearching)
    }

    var saveSourceState by rememberSaveable { mutableStateOf(true) }
    var importMimeTypeState by rememberSaveable { mutableStateOf("") }

    val pickFileContract = pickFileContract { onImport(it, saveSourceState) }
    val exportContract = exportContract { toExport(optionsItem.id, it) }

    var dialogNewFolder by newFolderDialog(onCreate = onCreateFolder)
    var dialogRenameState by renameDialog(optionsItem.name) { onRename(optionsItem.id, it) }
    var dialogDeleteState by deleteDialog(optionsItem.name) { onDelete(optionsItem.id) }
    var dialogDeleteSourceState by deleteSourceDialog { saveSource ->
        saveSourceState = saveSource
        pickFileContract.launch(arrayOf(importMimeTypeState))
    }

    filesCreateNewSheet(
        state = sheetCreateState,
        onCreateFolder = { dialogNewFolder = true },
        onAdd = {
            importMimeTypeState = "$it/*"
            dialogDeleteSourceState = true
        },
        onScan = onScan,
    )

    filesOptionsSheet(
        state = sheetOptionsState,
        name = optionsItem.name,
        itemIcon = optionsItem.fileType.icon,
        isFolder = optionsItem.isFolder,
        isStarred = optionsItem.isStarred,
        onOptionClick = {
            sheetOptionsState.value = false
            when (it) {
                Option.Open -> onOpen()
                Option.Export -> exportContract.launch(null)
                Option.Rename -> dialogRenameState = true
                Option.Delete -> dialogDeleteState = true
                Option.Star -> onStar(optionsItem.id, optionsItem.isStarred.not())
                Option.Select -> TODO()
                Option.Details -> toDetails(optionsItem.id)
            }
        }
    )
}

private fun pagingFakeData(): Flow<PagingData<FileItem>> {
    // create list of fake data for preview
    val fakeData = List(10) {
        var isFolder = it <= 4
        FileItem(
            id = it.toLong(),
            name = "Item $it",
            type = if (isFolder) FileType.Folder else FileType.entries.random(),
            isFolder = isFolder,
            isFile = isFolder.not()
        )
    }
    // create pagingData from a list of fake data
    val pagingData = PagingData.from(fakeData)
    // pass pagingData containing fake data to a MutableStateFlow
    return MutableStateFlow(pagingData)
}