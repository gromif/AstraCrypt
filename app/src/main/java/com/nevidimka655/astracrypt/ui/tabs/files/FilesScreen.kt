package com.nevidimka655.astracrypt.ui.tabs.files

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.entities.CoilTinkModel
import com.nevidimka655.astracrypt.entities.NavigatorDirectory
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.tabs.Tabs
import com.nevidimka655.astracrypt.tabs.files.createNewSheet
import com.nevidimka655.astracrypt.tabs.files.newFolder
import com.nevidimka655.astracrypt.ui.dialogs.DeleteFile
import com.nevidimka655.astracrypt.ui.dialogs.DeleteOriginalFiles
import com.nevidimka655.astracrypt.ui.dialogs.Dialogs
import com.nevidimka655.astracrypt.ui.dialogs.rename
import com.nevidimka655.astracrypt.ui.shared.NoItemsPage
import com.nevidimka655.astracrypt.ui.sheets.Sheets
import com.nevidimka655.astracrypt.ui.sheets.filesOptions
import com.nevidimka655.astracrypt.utils.IO
import com.nevidimka655.astracrypt.utils.appearance.AppearanceManager
import com.nevidimka655.astracrypt.utils.appearance.ViewMode
import com.nevidimka655.astracrypt.utils.enums.StorageItemState
import com.nevidimka655.astracrypt.utils.enums.StorageItemType
import com.nevidimka655.astracrypt.utils.extensions.removeLines
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.haptic.hapticLongClick
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.cellsCount
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesGridItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    name: String,
    thumb: String,
    thumbEncryptionType: Int,
    itemType: StorageItemType,
    state: StorageItemState,
    isChecked: Boolean,
    onOptions: () -> Unit,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) = ElevatedCard(
    modifier = modifier.aspectRatio(1f),
    colors = if (isChecked) CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) else CardDefaults.elevatedCardColors(),
    shape = ShapeDefaults.Large
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongPress() }
            )
    ) {
        var loadOtherIcons by remember { mutableStateOf(thumb.isEmpty()) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
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
            } else AsyncImage(modifier = Modifier.fillMaxSize(),
                model = CoilTinkModel(
                    absolutePath = null,
                    path = thumb,
                    encryptionType = thumbEncryptionType
                ),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop,
                onState = {
                    loadOtherIcons = it is AsyncImagePainter.State.Success
                })
            this@Column.AnimatedVisibility(
                visible = state.isStarred,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    modifier = Modifier
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
        }
        if (loadOtherIcons) Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (itemType.isFile) Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = itemType.icon,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = name,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isChecked) Icon(
                    modifier = Modifier.size(24.dp),
                    painter = rememberVectorPainter(image = Icons.Filled.CheckCircle),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                ) else IconButton(
                    modifier = Modifier.fillMaxSize(),
                    onClick = onOptions
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = rememberVectorPainter(image = Icons.Filled.MoreVert),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesListItemMedium(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    name: String = "TEST_NAME",
    thumb: String = "",
    thumbEncryptionType: Int = -1,
    itemType: StorageItemType = StorageItemType.Document,
    state: StorageItemState = StorageItemState.Default,
    isChecked: Boolean = false,
    isBackgroundTransparent: Boolean = false,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val backgroundColor = animateColorAsState(
        targetValue = if (isChecked) {
            MaterialTheme.colorScheme.surfaceVariant
        } else Color.Unspecified, label = ""
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .run {
                if (!isBackgroundTransparent) background(color = backgroundColor.value)
                else this
            }
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .height(dimensionResource(id = R.dimen.filesListItemMediumHeight)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(dimensionResource(id = R.dimen.filesListItemMediumHeight)),
            contentAlignment = Alignment.Center
        ) {
            if (thumb.isEmpty()) {
                if (itemType == StorageItemType.Folder) Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = itemType.iconAlt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                ) else Image(
                    modifier = Modifier.size(60.dp),
                    imageVector = itemType.iconAlt,
                    contentDescription = null
                )
            } else AsyncImage(
                model = CoilTinkModel(
                    absolutePath = null,
                    path = thumb,
                    encryptionType = thumbEncryptionType
                ),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            this@Row.AnimatedVisibility(
                visible = state.isStarred,
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
                            color = MaterialTheme.colorScheme.onSurfaceVariant, shape = CircleShape
                        )
                        .padding(MaterialTheme.spaces.spaceAltSmall)
                        .size(14.dp),
                    painter = rememberVectorPainter(Icons.Filled.Star),
                    tint = MaterialTheme.colorScheme.surface,
                    contentDescription = null
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround
        ) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(
    pagingItems: LazyPagingItems<StorageItemListTuple>,
    listCheckedState: SnapshotStateMap<Long, Boolean>,
    imageLoader: ImageLoader,
    onOptions: (item: StorageItemListTuple) -> Unit,
    onClick: (item: StorageItemListTuple) -> Unit,
    onLongPress: (item: StorageItemListTuple) -> Unit
) {
    val view = LocalView.current
    val viewMode = remember { AppearanceManager.viewMode }
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
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection()),
        columns = GridCells.Fixed(cells),
        contentPadding = contentPadding,
        verticalArrangement = arrangement,
        horizontalArrangement = arrangement
    ) {
        if (viewMode == ViewMode.Grid) items(count = pagingItems.itemSnapshotList.size,
            key = { pagingItems[it]?.id ?: it }) { index ->
            pagingItems[index]?.let {
                FilesGridItem(modifier = Modifier.animateItemPlacement(),
                    imageLoader = imageLoader,
                    name = it.name,
                    thumb = it.thumbnail,
                    thumbEncryptionType = it.thumbnailEncryptionType,
                    itemType = it.itemType,
                    state = it.state,
                    isChecked = listCheckedState.getOrElse(it.id) { false },
                    onOptions = {
                        Haptic.rise()
                        onOptions.invoke(it)
                    },
                    onClick = { onClick.invoke(it) },
                    onLongPress = {
                        view.hapticLongClick()
                        onLongPress.invoke(it)
                    })
            }
        } else items(count = pagingItems.itemSnapshotList.size,
            key = { pagingItems[it]?.id ?: it }) { index ->
            pagingItems[index]?.let {
                FilesListItemMedium(modifier = Modifier.animateItemPlacement(),
                    imageLoader = imageLoader,
                    name = it.name,
                    thumb = it.thumbnail,
                    thumbEncryptionType = it.thumbnailEncryptionType,
                    itemType = it.itemType,
                    state = it.state,
                    isChecked = listCheckedState.getOrElse(it.id) { false },
                    onLongClick = {
                        view.hapticLongClick()
                        onOptions.invoke(it)
                    },
                    onClick = { onClick.invoke(it) })
            }
        }
    }
}

@Composable
fun FilesNavigator(
    filesNavigatorList: SnapshotStateList<NavigatorDirectory>,
    onClick: (index: Int?) -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(filesNavigatorList.size) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = MaterialTheme.spaces.spaceSmall),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilesNavigatorItem(
            isFirstItem = true,
            title = stringResource(id = R.string.home)
        ) { onClick(null) }
        filesNavigatorList.forEachIndexed { i, it ->
            FilesNavigatorItem(title = it.name) { onClick(i) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FilesNavigatorItem(
    isFirstItem: Boolean = false, title: String = "", onClick: () -> Unit = {}
) {
    if (!isFirstItem) VerticalDivider(
        modifier = Modifier
            .width(2.dp)
            .height(20.dp)
    )
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(vertical = 13.dp, horizontal = 10.dp),
        text = title,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleSmall,
        maxLines = 1
    )
}

private fun openItem(
    vm: MainVM,
    isStarred: Boolean,
    onOpenStarredDir: () -> Unit,
    onOpenFile: (Long) -> Unit,
    item: StorageItemListTuple
) {
    if (item.itemType.isFile) onOpenFile(item.id) else {
        //closeSearchView()
        if (isStarred) {
            vm.openDirectory(
                id = item.id,
                dirName = item.name,
                popBackStack = true
            )
            vm.triggerFilesListUpdate()
            onOpenStarredDir()
        } else {
            //if (vm.getUiState().fabState) fab.show()
            vm.openDirectory(
                id = item.id,
                dirName = item.name
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesScreen(
    vm: MainVM,
    filesVM: FilesViewModel,
    isStarred: Boolean,
    onFabClick: Channel<Any>,
    onNavigateUp: () -> Unit,
    onNavigateToDetails: (Long) -> Unit,
    onOpenStarredDir: () -> Unit,
    onOpenFile: (Long) -> Unit,
    onNavigatorClick: (index: Int?) -> Unit,
    onLongPress: (item: StorageItemListTuple) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val items = (if (isStarred) vm.starredPagingFlow else vm.pagingFlow).collectAsLazyPagingItems()
    val isEmptyPageVisible = remember {
        derivedStateOf { items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading }
    }
    var dialogNewFolder by Tabs.Files.Dialogs.newFolder(state = vm.dialogNewFolderState) {
        vm.newDirectory(it.removeLines().trim())
    }
    var dialogRename by Tabs.Files.Dialogs.rename(
        state = filesVM.dialogRenameState,
        name = filesVM.optionsItem.name
    ) {
        vm.rename(filesVM.optionsItem.id, it.removeLines().trim())
    }
    if (filesVM.dialogDeleteState.value) Tabs.Files.Dialogs.DeleteFile(
        state = filesVM.dialogDeleteState,
        name = filesVM.optionsItem.name
    ) { vm.delete(filesVM.optionsItem.id) }
    var isCreateSheetVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        onFabClick.receiveAsFlow().collectLatest {
            Haptic.rise()
            isCreateSheetVisible.value = true
        }
    }
    var backOverrideState by remember { mutableStateOf(!isStarred) }
    BackHandler(enabled = backOverrideState) {
        if (vm.isSearchActive()) {
            //closeSearchView() //TODO
        } else {
            if (vm.filesNavigatorList.isNotEmpty()) vm.closeDirectory()
            else {
                if (vm.selectorManager.isInitialized) {
                    vm.selectorManager.run {
                        closeActionMode()
                        clear()
                        clearViews()
                    }
                } else onNavigateUp()
            }
        }
    }

    var deleteOri by rememberSaveable { mutableStateOf(false) }
    if (deleteOri) Dialogs.DeleteOriginalFiles(
        onImportStartDelete = {
            deleteOri = false
            vm.lastUriListToImport?.let {
                vm.import(
                    uriList = it.toTypedArray(),
                    saveOriginalFiles = false
                )
            }
        },
        onImportStartSave = {
            deleteOri = false
            vm.lastUriListToImport?.let {
                vm.import(
                    uriList = it.toTypedArray(),
                    saveOriginalFiles = true
                )
            }
        }
    )

    val pickFileContract = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) {
        if (it.isNotEmpty()) scope.launch {
            withContext(Dispatchers.Main) {
                vm.lastUriListToImport = it
                deleteOri = true
            }
        }
    }

    fun callFileContract(mimeSubType: String = "*") =
        pickFileContract.launch(arrayOf("$mimeSubType/*"))

    val scanContract = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        if (it) vm.import(vm.lastUriToScan!!).invokeOnCompletion {
            vm.lastUriToScan = null
        }
    }
    Sheets.createNewSheet(
        state = isCreateSheetVisible,
        scope = scope,
        onCreateFolder = { dialogNewFolder = true },
        onAdd = { callFileContract() },
        onScan = {
            scanContract.launch(
                FileProvider.getUriForFile(
                    context,
                    "com.nevidimka655.astracrypt",
                    IO.getExportedCacheCameraFile()
                ).also { vm.lastUriToScan = it }
            )
        },
        onAddPhoto = { callFileContract("image") },
        onAddVideo = { callFileContract("video") },
        onAddMusic = { callFileContract("audio") }
    )
    Sheets.filesOptions(
        state = filesVM.sheetOptionsState,
        name = filesVM.optionsItem.name,
        itemIcon = filesVM.optionsItem.itemType.icon,
        isFolder = filesVM.optionsItem.isDirectory,
        isStarred = filesVM.optionsItem.state.isStarred,
        onRename = {
            filesVM.sheetOptionsState.value = false
            dialogRename = true
        },
        onDelete = {
            filesVM.sheetOptionsState.value = false
            filesVM.dialogDeleteState.value = true
        },
        onDetails = {
            filesVM.sheetOptionsState.value = false
            //closeSearchView() //TODO: Search
            onNavigateToDetails(filesVM.optionsItem.id)
        },
        onStarStateChange = {
            filesVM.sheetOptionsState.value = false
            vm.setStarredFlag(it, filesVM.optionsItem.id)
        }
    )
    Column {
        if (!isStarred && !vm.isSearchExpandedState) {
            AnimatedVisibility(visible = vm.filesNavigatorList.isNotEmpty()) {
                FilesNavigator(
                    filesNavigatorList = vm.filesNavigatorList, onClick = onNavigatorClick
                )
            }
        }
        AnimatedVisibility(
            visible = !isEmptyPageVisible.value, enter = fadeIn(), exit = ExitTransition.None
        ) {
            FilesList(
                pagingItems = items,
                listCheckedState = vm.selectorManager.itemsMapState,
                imageLoader = filesVM.imageLoader,
                onOptions = {
                    with(filesVM) {
                        optionsItem = it
                        sheetOptionsState.value = true
                    }
                },
                onClick = {
                    when {
                        vm.selectorManager.itemsMapState.isEmpty() -> openItem(
                            vm = vm,
                            isStarred = isStarred,
                            onOpenStarredDir = onOpenStarredDir,
                            onOpenFile = onOpenFile,
                            item = it
                        )

                        vm.selectorManager.blockItems -> {
                            val isNotSelected = !vm.selectorManager.getItemState(it.id)
                            if (isNotSelected && it.isDirectory) openItem(
                                vm = vm,
                                isStarred = isStarred,
                                onOpenStarredDir = onOpenStarredDir,
                                onOpenFile = onOpenFile,
                                item = it
                            )
                        }

                        //else -> initSelecting(it)
                    }
                },
                onLongPress = onLongPress
            )
        }
        AnimatedVisibility(
            visible = isEmptyPageVisible.value, enter = fadeIn(), exit = ExitTransition.None
        ) {
            when {
                isStarred -> NoItemsPage(
                    mainIcon = Icons.Outlined.StarOutline, actionIcon = Icons.Outlined.StarOutline
                )

                vm.isSearchActive() -> NoItemsPage(
                    mainIcon = Icons.Filled.Search,
                    actionIcon = Icons.Filled.Search,
                    title = R.string.noItemsTitleSearch,
                    summary = R.string.noItemsSummarySearch
                )

                else -> NoItemsPage()
            }
        }
    }
}