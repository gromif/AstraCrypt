package com.nevidimka655.astracrypt.view.composables.files

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.view.models.NavigatorDirectory
import com.nevidimka655.astracrypt.domain.model.db.StorageState
import com.nevidimka655.astracrypt.data.database.StorageItemType
import com.nevidimka655.astracrypt.data.database.PagerTuple
import com.nevidimka655.astracrypt.view.MainVM
import com.nevidimka655.astracrypt.view.composables.files.sheets.filesCreateNewSheet
import com.nevidimka655.astracrypt.view.composables.files.sheets.filesOptionsSheet
import com.nevidimka655.astracrypt.view.composables.components.NoItemsPage
import com.nevidimka655.astracrypt.view.composables.components.dialogs.DeleteFile
import com.nevidimka655.astracrypt.view.composables.components.dialogs.DeleteOriginalFiles
import com.nevidimka655.astracrypt.view.composables.components.dialogs.Dialogs
import com.nevidimka655.astracrypt.view.composables.components.dialogs.newFolder
import com.nevidimka655.astracrypt.view.composables.components.dialogs.rename
import com.nevidimka655.astracrypt.view.models.ViewMode
import com.nevidimka655.haptic.Haptic
import com.nevidimka655.haptic.hapticLongClick
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.cellsCount
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
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
    preview: String?,
    itemType: StorageItemType,
    state: StorageState,
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
        var loadOtherIcons by remember { mutableStateOf(preview == null) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f),
            contentAlignment = Alignment.Center
        ) {
            if (preview == null) Icon(
                modifier = Modifier.size(72.dp),
                imageVector = itemType.iconAlt,
                contentDescription = null,
                tint = itemType.iconTint
            ) else AsyncImage(modifier = Modifier.fillMaxSize(),
                model = CoilTinkModel(
                    absolutePath = null,
                    path = preview
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
                    imageVector = Icons.Filled.Star,
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
                if (itemType.isFile) Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = itemType.icon,
                    contentDescription = null,
                    tint = itemType.iconTint
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
                    imageVector = Icons.Filled.CheckCircle,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                ) else IconButton(
                    modifier = Modifier.fillMaxSize(),
                    onClick = onOptions
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Filled.MoreVert,
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
    preview: String? = null,
    itemType: StorageItemType = StorageItemType.Document,
    state: StorageState = StorageState.Default,
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
            if (preview == null) Icon(
                modifier = Modifier.size(60.dp),
                imageVector = itemType.iconAlt,
                contentDescription = null,
                tint = itemType.iconTint
            ) else AsyncImage(
                model = CoilTinkModel(
                    absolutePath = null,
                    path = preview
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
                    imageVector = Icons.Filled.Star,
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
    viewMode: ViewMode = ViewMode.Grid,
    pagingItems: LazyPagingItems<PagerTuple>,
    listCheckedState: SnapshotStateMap<Long, Boolean>,
    imageLoader: ImageLoader,
    onOptions: (item: PagerTuple) -> Unit,
    onClick: (item: PagerTuple) -> Unit,
    onLongPress: (item: PagerTuple) -> Unit
) {
    val view = LocalView.current
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
        if (viewMode == ViewMode.Grid) items(count = pagingItems.itemSnapshotList.size,
            key = { pagingItems[it]?.id ?: it }) { index ->
            pagingItems[index]?.let {
                FilesGridItem(modifier = Modifier.animateItem(),
                    imageLoader = imageLoader,
                    name = it.name,
                    preview = it.preview,
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
            key = { it }) { index ->
            pagingItems[index]?.let {
                FilesListItemMedium(modifier = Modifier.animateItem(),
                    imageLoader = imageLoader,
                    name = it.name,
                    preview = it.preview,
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
    item: PagerTuple
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
    viewMode: ViewMode,
    items: LazyPagingItems<PagerTuple>,
    isStarred: Boolean,
    dialogNewFolderState: MutableState<Boolean>,
    onFabClick: Flow<Any>,
    onNavigateUp: () -> Unit,
    onNavigateToDetails: (Long) -> Unit,
    onOpenStarredDir: () -> Unit,
    onOpenFile: (Long) -> Unit,
    onNewFolder: (String) -> Unit,
    onExport: (itemId: Long, outUri: Uri) -> Unit,
    onRename: (itemId: Long, newName: String) -> Unit,
    onNavigatorClick: (index: Int?) -> Unit,
    onLongPress: (item: PagerTuple) -> Unit
) {
    val scope = rememberCoroutineScope()
    var dialogNewFolder by Dialogs.newFolder(state = dialogNewFolderState) {
        onNewFolder(it.trim())
    }
    var dialogRename by Dialogs.rename(
        state = filesVM.dialogRenameState,
        name = filesVM.optionsItem.name
    ) { onRename(filesVM.optionsItem.id, it) }
    if (filesVM.dialogDeleteState.value) Dialogs.DeleteFile(
        state = filesVM.dialogDeleteState,
        name = filesVM.optionsItem.name
    ) { vm.delete(filesVM.optionsItem.id) }
    var isCreateSheetVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        onFabClick.collectLatest {
            Haptic.rise()
            isCreateSheetVisible.value = true
        }
    }
    BackHandler(enabled = !vm.isSearching && !isStarred && vm.filesNavigatorList.isNotEmpty()) {
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

    var deleteOri by rememberSaveable { mutableStateOf(false) }
    if (deleteOri) Dialogs.DeleteOriginalFiles(
        onImportStartDelete = {
            deleteOri = false
            vm.lastUriListToImport?.let {
                filesVM.import(
                    uriList = it.toTypedArray(),
                    saveOriginalFiles = false,
                    dirId = vm.currentFolderId
                )
            }
        },
        onImportStartSave = {
            deleteOri = false
            vm.lastUriListToImport?.let {
                filesVM.import(
                    uriList = it.toTypedArray(),
                    saveOriginalFiles = true,
                    dirId = vm.currentFolderId
                )
            }
        }
    )

    val pickFileContract =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) {
            if (it.isNotEmpty()) scope.launch {
                withContext(Dispatchers.Main) {
                    vm.lastUriListToImport = it
                    deleteOri = true
                }
            }
        }

    fun callFileContract(mimeSubType: String = "*") =
        pickFileContract.launch(arrayOf("$mimeSubType/*"))

    val scanContract =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) filesVM.importCameraScan(vm.currentFolderId)
        }
    val exportContract =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
            it?.let { onExport(filesVM.optionsItem.id, it) }
        }

    filesCreateNewSheet(
        state = isCreateSheetVisible,
        scope = scope,
        onCreateFolder = { dialogNewFolder = true },
        onAdd = { callFileContract() },
        onScan = { scanContract.launch(filesVM.getCameraScanOutputUri()) },
        onAddPhoto = { callFileContract("image") },
        onAddVideo = { callFileContract("video") },
        onAddMusic = { callFileContract("audio") }
    )
    filesOptionsSheet(
        state = filesVM.sheetOptionsState,
        name = filesVM.optionsItem.name,
        itemIcon = filesVM.optionsItem.itemType.icon,
        isFolder = filesVM.optionsItem.isDirectory,
        isStarred = filesVM.optionsItem.state.isStarred,
        onOpen = {
            filesVM.sheetOptionsState.value = false
            openItem(
                vm = vm,
                isStarred = isStarred,
                onOpenStarredDir = onOpenStarredDir,
                onOpenFile = onOpenFile,
                item = filesVM.optionsItem
            )
        },
        onExport = {
            filesVM.sheetOptionsState.value = false
            exportContract.launch(null)
        },
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
            filesVM.setStarredFlag(it, filesVM.optionsItem.id)
        }
    )
    Column {
        if (!isStarred && !vm.isSearching) {
            AnimatedVisibility(visible = vm.filesNavigatorList.isNotEmpty()) {
                FilesNavigator(
                    filesNavigatorList = vm.filesNavigatorList, onClick = onNavigatorClick
                )
            }
        }
        val isEmptyPageVisible = remember {
            derivedStateOf { items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading }
        }
        AnimatedVisibility(
            visible = !isEmptyPageVisible.value, enter = fadeIn(), exit = ExitTransition.None
        ) {
            FilesList(
                viewMode = viewMode,
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

                vm.isSearching -> NoItemsPage(
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