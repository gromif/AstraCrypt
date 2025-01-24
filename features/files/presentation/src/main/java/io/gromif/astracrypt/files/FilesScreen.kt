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
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.model.ContextualAction
import io.gromif.astracrypt.files.model.Mode
import io.gromif.astracrypt.files.model.StateHolder
import io.gromif.astracrypt.files.model.action.Actions
import io.gromif.astracrypt.files.model.action.FilesNavActions
import io.gromif.astracrypt.files.saver.rememberMultiselectStateList
import io.gromif.astracrypt.files.shared.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesScreen(
    startParentId: Long? = null,
    startParentName: String = "",
    mode: Mode,
    isStarred: Boolean,
    onContextualAction: Flow<ContextualAction>,
    searchQueryState: StateFlow<String>,
    onModeChange: (Mode) -> Unit = {},
    navActions: FilesNavActions,
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

    if (startParentId != null) {
        var recycled by rememberSaveable { mutableStateOf(false) }
        if (!recycled) LaunchedEffect(Unit) {
            vm.openDirectory(startParentId, startParentName)
            recycled = true
        }
    }

    var cameraScanUri by rememberSaveable { mutableStateOf(Uri.EMPTY) }
    val scanContract = scanContract { vm.import(cameraScanUri) }

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

    val backStackList = vm.parentBackStack
    val stateHolder = remember(isSearching, multiselectStateList, backStackList) {
        StateHolder(
            isStarred = isStarred,
            isSearching = isSearching,
            mode = mode,
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
        navActions = navActions,

        actions = object : Actions {
            override fun backStackClick(index: Int?) {
                vm.openDirectoryFromBackStack(index)
            }

            override fun click(item: FileItem) {
                val (id, _, name) = item
                when {
                    mode is Mode.Multiselect && multiselectStateList.isNotEmpty() -> selectItem(id)
                    item.isFolder -> if (isStarred) navActions.toFiles(id, name) else {
                        if (mode === Mode.Move && multiselectStateList.contains(id)) return
                        vm.openDirectory(id, name)
                    }
                }
            }

            override fun longClick(id: Long) {
                if (mode !== Mode.Move) selectItem(id)
            }

            override fun setMoveMode() = onModeChange(Mode.Move)
            override fun closeContextualToolbar() = closeContextualToolbar()

            override fun open() {
                TODO("Not yet implemented")
            }

            override fun createFolder(name: String) {
                vm.createFolder(name)
            }

            override fun import(uriList: Array<Uri>, saveSource: Boolean, ) {
                vm.import(*uriList, saveSource = saveSource)
            }

            override fun scan() {
                cameraScanUri = vm.getCameraScanOutputUri()
                scanContract.launch(cameraScanUri)
            }

            override fun move() {
                vm.move(ids = multiselectStateList.toList())
            }

            override fun star(state: Boolean, idList: List<Long>) {
                vm.setStarred(state, idList)
            }

            override fun rename(id: Long, name: String) {
                vm.rename(id, name)
            }

            override fun delete(idList: List<Long>) {
                vm.delete(idList)
            }
        },
        sheetCreateState = sheetCreateState,
    )
}