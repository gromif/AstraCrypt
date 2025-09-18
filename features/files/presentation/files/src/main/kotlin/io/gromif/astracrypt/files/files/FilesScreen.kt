package io.gromif.astracrypt.files.files

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.files.files.model.ContextualAction
import io.gromif.astracrypt.files.files.model.FilesInitialParams
import io.gromif.astracrypt.files.files.model.Mode
import io.gromif.astracrypt.files.files.model.StateHolder
import io.gromif.astracrypt.files.files.model.action.Actions
import io.gromif.astracrypt.files.files.model.action.BrowseActions
import io.gromif.astracrypt.files.files.model.action.FilesNavActions
import io.gromif.astracrypt.files.files.model.action.ImportActions
import io.gromif.astracrypt.files.files.model.action.factory.createBrowseActions
import io.gromif.astracrypt.files.files.model.action.factory.createImportActions
import io.gromif.astracrypt.files.files.model.action.factory.createItemActions
import io.gromif.astracrypt.files.files.model.action.factory.createToolbarActions
import io.gromif.astracrypt.files.files.util.contracts.Contracts
import io.gromif.astracrypt.files.files.util.contracts.scan
import io.gromif.astracrypt.files.files.util.saver.rememberMultiselectStateList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesScreen(
    initialParams: FilesInitialParams,
    mode: Mode,
    onContextualAction: Flow<ContextualAction>,
    snackbarHostState: SnackbarHostState,
    searchQueryState: StateFlow<String>,
    onModeChange: (Mode) -> Unit = {},
    navActions: FilesNavActions,
) {
    val vm: FilesViewModel = hiltViewModel()
    val validationRules = vm.validationRules
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val backStack by vm.navigationBackStackState.collectAsStateWithLifecycle()
    val viewMode by vm.viewModeState.collectAsStateWithLifecycle()
    val searchQuery by searchQueryState.collectAsStateWithLifecycle()
    var isSearching by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(searchQuery) {
        isSearching = searchQuery.isNotEmpty()
        vm.setSearchQuery(query = searchQuery)
    }
    BackHandler(enabled = !isSearching && backStack.isNotEmpty()) {
        vm.closeDirectory()
    }
    AcknowledgeInitialParams(vm = vm, initialParams = initialParams)

    val multiselectStateList = rememberMultiselectStateList()
    fun selectItem(id: Long) = with(multiselectStateList) {
        if (contains(id)) remove(id) else add(id)
        val newMode = if (isNotEmpty()) Mode.Multiselect(count = size) else Mode.Default
        onModeChange(newMode)
    }

    fun closeContextualToolbar() {
        multiselectStateList.clear()
        onModeChange(Mode.Default)
    }
    BackHandler(enabled = mode is Mode.Multiselect, onBack = ::closeContextualToolbar)

    val stateHolder = remember(isSearching, multiselectStateList, backStack, viewMode) {
        StateHolder(
            isStarred = initialParams.isStarred,
            isSearching = isSearching,
            mode = mode,
            viewMode = viewMode,
            pagingFlow = if (initialParams.isStarred && backStack.isEmpty()) {
                vm.pagingStarredFlow
            } else {
                vm.pagingFlow
            },
            multiselectStateList = multiselectStateList,
            backStackList = backStack,
        )
    }

    fun showSnackbar(@StringRes stringId: Int) {
        scope.launch { snackbarHostState.showSnackbar(context.getString(stringId)) }
    }

    val browseActions = Actions.createBrowseActions(
        vm = vm,
        navActions = navActions,
        state = BrowseActions.State(
            mode = mode,
            multiselectStateList = multiselectStateList,
            isStarred = initialParams.isStarred
        ),
        onSelectItem = ::selectItem
    )
    val importActions = createImportActions(vm = vm, onMessage = ::showSnackbar)
    val itemActions = Actions.createItemActions(
        vm = vm,
        onMessage = ::showSnackbar,
        multiselectStateList = multiselectStateList
    )
    val toolbarActions = Actions.createToolbarActions(
        onCloseContextualToolbar = ::closeContextualToolbar,
        onModeChange = onModeChange
    )

    Screen(
        stateHolder = stateHolder,
        onContextualAction = onContextualAction,
        imageLoader = vm.imageLoader,
        actions = Actions.Holder(
            browseActions = browseActions,
            importActions = importActions,
            itemActions = itemActions,
            toolbarActions = toolbarActions,
            navigation = navActions
        ),
        maxNameLength = validationRules.maxNameLength
    )
}

@Composable
private fun AcknowledgeInitialParams(
    vm: FilesViewModel,
    initialParams: FilesInitialParams
) {
    if (initialParams.startParentId != null) {
        var recycled by rememberSaveable { mutableStateOf(false) }
        if (!recycled) {
            LaunchedEffect(Unit) {
                vm.openDirectory(initialParams.startParentId, initialParams.startParentName)
                recycled = true
            }
        }
    }
}

@Composable
private fun createImportActions(
    vm: FilesViewModel,
    onMessage: (Int) -> Unit
): ImportActions {
    var cameraScanUri by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    val scanContract = Contracts.scan { vm.import(cameraScanUri) }

    return Actions.createImportActions(
        vm = vm,
        onScan = {
            cameraScanUri = vm.getCameraScanOutputUri()
            scanContract.launch(cameraScanUri)
        },
        onMessage = onMessage
    )
}
