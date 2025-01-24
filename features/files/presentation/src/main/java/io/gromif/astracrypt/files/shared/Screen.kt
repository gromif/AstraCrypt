package io.gromif.astracrypt.files.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import io.gromif.astracrypt.files.model.ContextualAction
import io.gromif.astracrypt.files.model.Mode
import io.gromif.astracrypt.files.model.Option
import io.gromif.astracrypt.files.model.OptionsItem
import io.gromif.astracrypt.files.model.StateHolder
import io.gromif.astracrypt.files.model.action.Actions
import io.gromif.astracrypt.files.model.action.FilesNavActions
import io.gromif.astracrypt.files.shared.list.EmptyList
import io.gromif.astracrypt.files.shared.list.FilesList
import io.gromif.astracrypt.files.shared.sheet.filesCreateNewSheet
import io.gromif.astracrypt.files.shared.sheet.filesOptionsSheet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
internal fun Screen(
    stateHolder: StateHolder = StateHolder(pagingFlow = pagingFakeData()),
    onContextualAction: Flow<ContextualAction> = emptyFlow(),
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    navActions: FilesNavActions = FilesNavActions.Empty,
    actions: Actions = Actions.Default,
    sheetCreateState: MutableState<Boolean> = mutableStateOf(false),
) = Column {
    val sheetOptionsState = Compose.state()
    var optionsItem by rememberSaveable { mutableStateOf(OptionsItem()) }
    val items = stateHolder.pagingFlow.collectAsLazyPagingItems()

    if (!stateHolder.isSearching) AnimatedVisibility(stateHolder.backStackList.isNotEmpty()) {
        FilesBackStackList(stateHolder.backStackList, actions::backStackClick)
    }
    val isEmpty = remember {
        derivedStateOf { items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading }
    }
    AnimatedVisibility(visible = !isEmpty.value, enter = fadeIn(), exit = ExitTransition.None) {
        FilesList(
            viewMode = stateHolder.viewMode,
            pagingItems = items,
            multiselectStateList = stateHolder.multiselectStateList,
            imageLoader = imageLoader,
            onOptions = onOptions@{
                if (stateHolder.mode is Mode.Move) return@onOptions
                optionsItem = OptionsItem(
                    id = it.id,
                    name = it.name,
                    isStarred = it.state == FileState.Starred,
                    fileType = it.type,
                    isFolder = it.isFolder
                )
                sheetOptionsState.value = true
            },
            onClick = actions::click,
            onLongPress = actions::longClick
        )
    }
    AnimatedVisibility(visible = isEmpty.value, enter = fadeIn(), exit = ExitTransition.None) {
        EmptyList(stateHolder.isStarred, stateHolder.isSearching)
    }

    var saveSourceState by rememberSaveable { mutableStateOf(true) }
    var importMimeTypeState by rememberSaveable { mutableStateOf("") }

    val pickFileContract = pickFileContract { actions.import(it.toTypedArray(), saveSourceState) }
    val exportContract = exportContract { navActions.toExport(optionsItem.id, it) }

    var dialogNewFolder by newFolderDialog(onCreate = actions::createFolder)
    var dialogRenameState by renameDialog(optionsItem.name) { actions.rename(optionsItem.id, it) }
    var dialogDeleteState by deleteDialog(optionsItem.name) { actions.delete(listOf(optionsItem.id)) }
    var dialogDeleteSourceState by deleteSourceDialog { saveSource ->
        saveSourceState = saveSource
        pickFileContract.launch(arrayOf(importMimeTypeState))
    }

    LaunchedEffect(Unit) {
        onContextualAction.collectLatest {
            when (it) {
                ContextualAction.CreateFolder -> dialogNewFolder = true
                ContextualAction.MoveNavigation -> actions.setMoveMode()
                ContextualAction.Move -> {
                    actions.move()
                    actions.closeContextualToolbar()
                }
                ContextualAction.Close -> actions.closeContextualToolbar()
                ContextualAction.Star -> {
                    actions.star(true, stateHolder.multiselectStateList.toList())
                    actions.closeContextualToolbar()
                }

                ContextualAction.Unstar -> {
                    actions.star(false, stateHolder.multiselectStateList.toList())
                    actions.closeContextualToolbar()
                }

                ContextualAction.Delete -> {
                    actions.delete(stateHolder.multiselectStateList.toList())
                    actions.closeContextualToolbar()
                }
            }
        }
    }

    filesCreateNewSheet(
        state = sheetCreateState,
        onCreateFolder = { dialogNewFolder = true },
        onAdd = {
            importMimeTypeState = "$it/*"
            dialogDeleteSourceState = true
        },
        onScan = actions::scan,
    )

    filesOptionsSheet(
        state = sheetOptionsState,
        name = optionsItem.name,
        itemIcon = optionsItem.fileType.icon,
        isFolder = optionsItem.isFolder,
        isStarred = optionsItem.isStarred,
    ) {
        sheetOptionsState.value = false
        val (id, _, isStarred) = optionsItem
        when (it) {
            Option.Open -> actions.open()
            Option.Export -> exportContract.launch(null)
            Option.Rename -> dialogRenameState = true
            Option.Delete -> dialogDeleteState = true
            Option.Star -> actions.star(state = !isStarred, idList = listOf(id))
            Option.Select -> actions.longClick(id)
            Option.Details -> navActions.toDetails(id)
        }
    }
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