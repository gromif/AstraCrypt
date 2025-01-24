package io.gromif.astracrypt.files

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.ImageLoader
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.astracrypt.utils.io.WorkerSerializer
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.di.FilesImageLoader
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.provider.PagingProvider
import io.gromif.astracrypt.files.domain.usecase.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.GetListViewModeUseCase
import io.gromif.astracrypt.files.domain.usecase.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.SetStarredUseCase
import io.gromif.astracrypt.files.model.RootInfo
import io.gromif.astracrypt.files.work.ImportFilesWorker
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private typealias Args = ImportFilesWorker.Args

private const val ROOT_CURRENT = "root_current"
private const val ROOT_BACK_STACK = "root_back_stack"

@HiltViewModel
class FilesViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val pagingProvider: PagingProvider<PagingData<FileItem>>,
    private val createFolderUseCase: CreateFolderUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val moveUseCase: MoveUseCase,
    private val renameUseCase: RenameUseCase,
    private val setStarredUseCase: SetStarredUseCase,
    private val workManager: WorkManager,
    private val workerSerializer: WorkerSerializer,
    val filesUtil: FilesUtil,
    @FilesImageLoader
    val imageLoader: ImageLoader,
    getListViewModeUseCase: GetListViewModeUseCase,
) : ViewModel() {
    private val parentIdState = MutableStateFlow(0L)
    private val parentBackStackMutable = mutableStateListOf<RootInfo>()
    val parentBackStack: List<RootInfo> = parentBackStackMutable

    val pagingFlow = pagingProvider.provide(
        parentIdState = parentIdState
    ).cachedIn(viewModelScope)

    val pagingStarredFlow = pagingProvider.provide(
        parentIdState = parentIdState,
        isStarredMode = true
    ).cachedIn(viewModelScope)

    val viewModeState = getListViewModeUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, ViewMode.Grid)

    fun setParentId(id: Long) = parentIdState.update { id }

    suspend fun setSearchQuery(query: String) = with(pagingProvider) {
        setSearchQuery(parentId = parentIdState.value, query = query)
    }

    init {
        val savedRootBackStack: List<RootInfo>? = state[ROOT_BACK_STACK]
        if (savedRootBackStack != null) {
            parentBackStackMutable.addAll(savedRootBackStack)
        }
        val savedCurrentRoot: Long? = state[ROOT_CURRENT]
        if (savedCurrentRoot != null) setParentId(id = savedCurrentRoot)
    }

    private var searchSetupJob: Job? = null
    private var searchDirsIndexesList = mutableStateListOf<Long>()

    private var _searchChannel: Channel<String>? = null

    fun openDirectory(
        id: Long,
        name: String,
        popBackStack: Boolean = false,
    ) = viewModelScope.launch(defaultDispatcher) {
        val newName = if (name.length > 20) "${name.take(20)}.." else name
        val rootInfo = RootInfo(id, newName)
        with(parentBackStackMutable) {
            if (popBackStack) clear()
            add(rootInfo)
        }
        setParentId(id = id)
        pagingProvider.invalidate()
    }

    fun openDirectoryFromBackStack(index: Int?) = viewModelScope.launch(defaultDispatcher) {
        if (index == null) {
            parentBackStackMutable.clear()
            setParentId(id = 0)
            pagingProvider.invalidate()
            return@launch
        } else {
            val selectedNavigatorDir = parentBackStackMutable[index]
            if (parentIdState.value != selectedNavigatorDir.id) {
                parentBackStackMutable.removeRange(index + 1, parentBackStackMutable.size)
                setParentId(id = parentBackStackMutable.last().id)
                pagingProvider.invalidate()
            }
        }
    }

    fun closeDirectory() {
        parentBackStackMutable.removeAt(parentBackStackMutable.lastIndex)
        setParentId(id = parentBackStack.lastOrNull()?.id ?: 0L)
        pagingProvider.invalidate()
    }

    fun createFolder(name: String) = viewModelScope.launch(defaultDispatcher) {
        createFolderUseCase(
            name = name,
            parentId = parentIdState.value
        )
        //showSnackbar(R.string.snack_folderCreated)
    }

    fun delete(ids: List<Long>) = viewModelScope.launch(defaultDispatcher) {
        deleteUseCase(ids)
        /*showSnackbar(
            if (itemToDelete.path.isEmpty()) R.string.snack_folderDeleted
            else R.string.snack_itemsDeleted
        )*/
    }

    fun move(ids: List<Long>, parentId: Long?) = viewModelScope.launch(defaultDispatcher) {
        moveUseCase(ids = ids, parentId = parentId ?: 0)
        //showSnackbar(R.string.snack_itemsMoved)
    }

    fun getCameraScanOutputUri(): Uri =
        filesUtil.getExportedCacheFileUri(file = filesUtil.getExportedCacheCameraFile())

    fun setStarred(
        state: Boolean,
        ids: List<Long>,
    ) = viewModelScope.launch(defaultDispatcher) {
        setStarredUseCase(ids = ids, state = state)
        /*showSnackbar(
            if (state) R.string.snack_starred else R.string.snack_unstarred
        )*/ // TODO: Snackbar
    }

    fun import(
        vararg uriList: Uri,
        saveSource: Boolean = false,
    ) = viewModelScope.launch(defaultDispatcher) {
        val listToSave = uriList.map { it.toString() }
        val fileWithUris = workerSerializer.saveStringListToFile(listToSave)
        val rootId = parentIdState.value
        val data = workDataOf(
            Args.URI_FILE to fileWithUris.toString(),
            Args.PARENT_ID to rootId,
            Args.SAVE_SOURCE to saveSource
        )
        val workerRequest = OneTimeWorkRequestBuilder<ImportFilesWorker>().apply {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
        workManager.getWorkInfoByIdFlow(workerRequest.id).collectLatest {
            when (it?.state) {
                WorkInfo.State.SUCCEEDED -> {
//                    showSnackbar(R.string.snack_imported) TODO: Snackbar
                    cancel()
                }

                WorkInfo.State.FAILED -> {
                    cancel()
                }

                else -> {}
            }
        }
    }

    fun rename(id: Long, newName: String) = viewModelScope.launch(defaultDispatcher) {
        renameUseCase(id = id, newName = newName)
        //showSnackbar(R.string.snack_itemRenamed) TODO: Snackbar
    }

    override fun onCleared() {
        state[ROOT_CURRENT] = parentIdState.value
        state[ROOT_BACK_STACK] = parentBackStack.toList()
    }
}