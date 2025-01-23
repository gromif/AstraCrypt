package io.gromif.astracrypt.files

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.files.contracts.scanContract
import io.gromif.astracrypt.files.model.ContextualAction
import io.gromif.astracrypt.files.model.Mode
import io.gromif.astracrypt.files.model.StateHolder
import io.gromif.astracrypt.files.saver.rememberMultiselectStateList
import io.gromif.astracrypt.files.shared.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesScreen(
    isStarred: Boolean,
    onContextualAction: Flow<ContextualAction>,
    searchQueryState: StateFlow<String>,
    onModeChange: (Mode) -> Unit = {},
    toExport: (id: Long, output: Uri) -> Unit = { _, _ -> },
    toDetails: (id: Long) -> Unit = {},
    sheetCreateState: MutableState<Boolean>,
) {
    val vm: FilesViewModel = hiltViewModel()
    val viewMode by vm.viewModeState.collectAsStateWithLifecycle()
    val searchQuery by searchQueryState.collectAsStateWithLifecycle()
    var isSearching by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(searchQuery) {
        isSearching = searchQuery.isNotEmpty()
        vm.setSearchQuery(query = searchQuery)
    }
    BackHandler(enabled = !isSearching && vm.parentBackStack.isNotEmpty()) {
        vm.closeDirectory()
    }

    var cameraScanUri by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    val scanContract = scanContract { vm.import(cameraScanUri) }

    val multiselectStateList = rememberMultiselectStateList()
    fun selectItem(id: Long) = with(multiselectStateList) {
        if (contains(id)) remove(id) else add(id)
    }

    LaunchedEffect(multiselectStateList.size) {
        val newMode = when {
            multiselectStateList.isNotEmpty() -> Mode.Multiselect(count = multiselectStateList.size)
            else -> Mode.Default
        }
        onModeChange(newMode)
    }

    val backStackList = vm.parentBackStack
    val stateHolder = remember(isSearching, multiselectStateList, backStackList) {
        StateHolder(
            isStarred = isStarred,
            isSearching = isSearching,
            viewMode = viewMode,
            pagingFlow = run {
                if (isStarred && backStackList.isEmpty()) vm.pagingStarredFlow else vm.pagingFlow
            },
            multiselectStateList = multiselectStateList,
            backStackList = backStackList,
        )
    }
    Screen(
        stateHolder = stateHolder,
        onContextualAction = onContextualAction,
        imageLoader = vm.imageLoader,

        onBackStackClick = vm::openDirectoryFromBackStack,
        onClick = {
            when {
                multiselectStateList.isNotEmpty() -> selectItem(id = it.id)
                it.isFolder -> vm.openDirectory(id = it.id, name = it.name)
            }
        },
        onLongPress = ::selectItem,
        onImport = vm::import,
        onScan = {
            cameraScanUri = vm.getCameraScanOutputUri()
            scanContract.launch(cameraScanUri)
        },
        onOpen = {},
        onCreateFolder = vm::createFolder,
        onRename = vm::rename,
        onStar = vm::setStarred,
        onDelete = vm::delete,

        sheetCreateState = sheetCreateState,

        toExport = toExport,
        toDetails = toDetails
    )
}