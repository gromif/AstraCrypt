package io.gromif.astracrypt.files

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.files.contracts.scanContract
import io.gromif.astracrypt.files.saver.rememberMultiselectStateList
import io.gromif.astracrypt.files.shared.Screen
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesScreen(
    isStarred: Boolean,
    searchQueryState: StateFlow<String>,
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

    val backStackList = vm.parentBackStack
    val pagingFlow = if (isStarred && backStackList.isEmpty()) {
        vm.pagingStarredFlow
    } else vm.pagingFlow

    val multiselectStateList = rememberMultiselectStateList()
    fun selectItem(id: Long) = with(multiselectStateList) {
        if (contains(id)) remove(id) else add(id)
    }

    Screen(
        isStarred = isStarred,
        isSearching = isSearching,
        multiselectStateList = multiselectStateList,
        viewMode = viewMode,
        imageLoader = vm.imageLoader,

        backStackList = backStackList,
        onBackStackClick = {
            vm.openDirectoryFromBackStack(index = it)
        },
        pagingFlow = pagingFlow,

        onClick = {
            when {
                multiselectStateList.isNotEmpty() -> selectItem(id = it.id)
                it.isFolder -> vm.openDirectory(id = it.id, name = it.name)
            }
        },
        onLongPress = { selectItem(id = it.id) },
        onImport = { sourceUris, saveSource ->
            vm.import(uriList = sourceUris.toTypedArray(), saveSource = saveSource)
        },
        onScan = {
            cameraScanUri = vm.getCameraScanOutputUri()
            scanContract.launch(cameraScanUri)
        },
        onOpen = {},
        onCreateFolder = { vm.createFolder(name = it) },
        onRename = { id, name -> vm.rename(id = id, newName = name) },
        onStar = { id, state -> vm.setStarred(state, listOf(id)) },
        onDelete = { vm.delete(ids = listOf(it)) },

        sheetCreateState = sheetCreateState,

        toExport = toExport,
        toDetails = toDetails
    )
}