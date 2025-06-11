package io.gromif.astracrypt.files.files

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.ImageLoader
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.files.dialogs.deleteDialog
import io.gromif.astracrypt.files.files.dialogs.deleteSourceDialog
import io.gromif.astracrypt.files.files.dialogs.newFolderDialog
import io.gromif.astracrypt.files.files.dialogs.renameDialog
import io.gromif.astracrypt.files.files.list.EmptyList
import io.gromif.astracrypt.files.files.list.FilesBackStackList
import io.gromif.astracrypt.files.files.list.FilesList
import io.gromif.astracrypt.files.files.model.ContextualAction
import io.gromif.astracrypt.files.files.model.Mode
import io.gromif.astracrypt.files.files.model.Option
import io.gromif.astracrypt.files.files.model.OptionsItem
import io.gromif.astracrypt.files.files.model.StateHolder
import io.gromif.astracrypt.files.files.model.action.Actions
import io.gromif.astracrypt.files.files.model.action.BrowseActions
import io.gromif.astracrypt.files.files.model.action.FilesNavActions
import io.gromif.astracrypt.files.files.model.action.ImportActions
import io.gromif.astracrypt.files.files.sheet.filesCreateNewSheet
import io.gromif.astracrypt.files.files.sheet.filesOptionsSheet
import io.gromif.astracrypt.files.files.util.contracts.Contracts
import io.gromif.astracrypt.files.files.util.contracts.export
import io.gromif.astracrypt.files.files.util.contracts.pickFile
import io.gromif.astracrypt.files.shared.FakeData
import io.gromif.astracrypt.files.shared.icon
import io.gromif.ui.compose.core.Compose
import io.gromif.ui.compose.core.ext.FlowObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
internal fun Screen(
    stateHolder: StateHolder = StateHolder(pagingFlow = FakeData.paging()),
    onContextualAction: Flow<ContextualAction> = emptyFlow(),
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    actions: Actions.Holder = Actions.Holder(),
    maxNameLength: Int = 128,
) = Column {
    var optionsItem by rememberSaveable { mutableStateOf(OptionsItem()) }
    val items = stateHolder.pagingFlow.collectAsLazyPagingItems()
    if (!stateHolder.isSearching) {
        AnimatedVisibility(stateHolder.backStackList.isNotEmpty()) {
            FilesBackStackList(stateHolder.backStackList, actions.browseActions::backStackClick)
        }
    }

    val sheetOptionsState = optionsSheetIntegration(
        optionsItem = optionsItem,
        navActions = actions.navigation,
        actions = actions,
        maxNameLength = maxNameLength
    )

    FilesListIntegration(
        items = items,
        imageLoader = imageLoader,
        stateHolder = stateHolder,
        browseActions = actions.browseActions,
        onOpenOptions = {
            optionsItem = it
            sheetOptionsState.value = true
        }
    )

    var dialogNewFolder by newFolderDialog(
        maxLength = maxNameLength,
        onCreate = actions.itemActions::createFolder,
    )

    val sheetCreateState = createSheetIntegration(
        onCreateFolder = { dialogNewFolder = true },
        importActions = actions.importActions
    )
    FlowObserver(onContextualAction) {
        val multiselectList = stateHolder.multiselectStateList.toList()
        when (it) {
            ContextualAction.Close -> actions.toolbarActions.closeContextualToolbar()
            ContextualAction.Add -> sheetCreateState.value = true
            ContextualAction.CreateFolder -> dialogNewFolder = true
            ContextualAction.Delete -> actions.itemActions.delete(multiselectList)
            ContextualAction.Move -> actions.itemActions.move()
            ContextualAction.MoveNavigation -> actions.toolbarActions.setMoveMode()
            is ContextualAction.Star -> actions.itemActions.star(it.state, multiselectList)
        }
        if (it.resetMode) actions.toolbarActions.closeContextualToolbar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun createSheetIntegration(
    onCreateFolder: () -> Unit,
    importActions: ImportActions
): MutableState<Boolean> {
    var saveSourceState by rememberSaveable { mutableStateOf(true) }
    val pickFileContract = Contracts.pickFile {
        importActions.import(it.toTypedArray(), saveSourceState)
    }
    var importMimeTypeState by rememberSaveable { mutableStateOf("") }
    var dialogDeleteSourceState by deleteSourceDialog { saveSource ->
        saveSourceState = saveSource
        pickFileContract.launch(arrayOf(importMimeTypeState))
    }

    return filesCreateNewSheet(
        state = Compose.state(),
        onCreateFolder = onCreateFolder,
        onAdd = {
            importMimeTypeState = "$it/*"
            dialogDeleteSourceState = true
        },
        onScan = importActions::scan,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun optionsSheetIntegration(
    sheetOptionsState: MutableState<Boolean> = Compose.state(),
    optionsItem: OptionsItem,
    navActions: FilesNavActions = FilesNavActions(),
    actions: Actions.Holder,
    maxNameLength: Int = 128,
): MutableState<Boolean> {
    val exportContract = Contracts.export { navActions.toExport(optionsItem.id, it) }
    var dialogRenameState by renameDialog(
        maxLength = maxNameLength,
        name = optionsItem.name
    ) { actions.itemActions.rename(optionsItem.id, it) }
    var dialogDeleteState by deleteDialog(optionsItem.name) {
        actions.itemActions.delete(listOf(optionsItem.id))
    }

    return filesOptionsSheet(
        state = sheetOptionsState,
        name = optionsItem.name,
        itemIcon = optionsItem.itemType.icon,
        isFolder = optionsItem.isFolder,
        isStarred = optionsItem.isStarred,
    ) {
        sheetOptionsState.value = false
        val (id, _, isStarred) = optionsItem
        when (it) {
            Option.Open -> actions.browseActions.open(id)
            Option.Export -> exportContract.launch(null)
            Option.Rename -> dialogRenameState = true
            Option.Delete -> dialogDeleteState = true
            Option.Star -> actions.itemActions.star(state = !isStarred, idList = listOf(id))
            Option.Select -> actions.browseActions.longClick(id)
            Option.Details -> navActions.toDetails(id)
        }
    }
}

@Composable
private fun FilesListIntegration(
    items: LazyPagingItems<Item>,
    imageLoader: ImageLoader,
    stateHolder: StateHolder,
    browseActions: BrowseActions,
    onOpenOptions: (OptionsItem) -> Unit
) {
    val isEmpty by remember {
        derivedStateOf { items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading }
    }

    AnimatedVisibility(visible = isEmpty, enter = fadeIn(), exit = ExitTransition.None) {
        EmptyList(stateHolder.isStarred, stateHolder.isSearching)
    }

    AnimatedVisibility(
        visible = !isEmpty,
        enter = fadeIn(),
        exit = ExitTransition.None
    ) {
        FilesList(
            viewMode = stateHolder.viewMode,
            pagingItems = items,
            multiselectStateList = stateHolder.multiselectStateList,
            imageLoader = imageLoader,
            onOptions = onOptions@{
                if (stateHolder.mode is Mode.Move) return@onOptions
                val optionsItem = OptionsItem(
                    id = it.id,
                    name = it.name,
                    isStarred = it.state == ItemState.Starred,
                    itemType = it.type,
                    isFolder = it.isFolder
                )
                onOpenOptions(optionsItem)
            },
            onClick = browseActions::click,
            onLongPress = browseActions::longClick
        )
    }
}
