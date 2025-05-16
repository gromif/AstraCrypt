package io.gromif.astracrypt.files.files

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
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.di.coil.FilesImageLoader
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.preferences.GetListViewModeUseCase
import io.gromif.astracrypt.files.files.model.RootInfo
import io.gromif.astracrypt.files.files.util.ActionUseCases
import io.gromif.astracrypt.files.work.ImportFilesWorker
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.astracrypt.utils.io.FilesUtil
import io.gromif.astracrypt.utils.io.WorkerSerializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
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
internal class FilesViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val dataSource: DataSource<PagingData<Item>>,
    private val actionUseCases: ActionUseCases,
    private val workManager: WorkManager,
    private val workerSerializer: WorkerSerializer,
    val filesUtil: FilesUtil,
    @FilesImageLoader
    val imageLoader: ImageLoader,
    getListViewModeUseCase: GetListViewModeUseCase,
    getValidationRulesUsecase: GetValidationRulesUseCase,
) : ViewModel() {
    private val parentIdState = MutableStateFlow(0L)
    private val parentBackStackMutable = mutableStateListOf<RootInfo>()
    val parentBackStack: List<RootInfo> = parentBackStackMutable
    private var parentId
        get() = parentIdState.value
        set(value) = parentIdState.update { value }

    val pagingFlow = dataSource.provide(parentIdState).cachedIn(viewModelScope)
    val pagingStarredFlow = dataSource.provide(
        parentIdState = parentIdState,
        isStarredMode = true
    ).cachedIn(viewModelScope)

    val validationRules = getValidationRulesUsecase()
    val viewModeState = getListViewModeUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewMode.Grid)

    suspend fun setSearchQuery(query: String) = dataSource.setSearchQuery(parentId, query)

    fun openDirectory(id: Long, name: String) = viewModelScope.launch(defaultDispatcher) {
        val maxLength = validationRules.maxBackstackNameLength
        val newName = if (name.length > maxLength) "${name.take(20)}.." else name
        val rootInfo = RootInfo(id, newName)
        parentBackStackMutable.add(rootInfo)
        parentId = id
        dataSource.invalidate()
    }

    fun openDirectoryFromBackStack(index: Int?) = viewModelScope.launch(defaultDispatcher) {
        if (index == null) {
            parentBackStackMutable.clear()
            parentId = 0
            dataSource.invalidate()
        } else {
            val selectedNavigatorDir = parentBackStackMutable[index]
            if (parentId != selectedNavigatorDir.id) {
                parentBackStackMutable.removeRange(index + 1, parentBackStackMutable.size)
                parentId = parentBackStackMutable.last().id
                dataSource.invalidate()
            }
        }
    }

    fun closeDirectory() {
        parentBackStackMutable.removeAt(parentBackStackMutable.lastIndex)
        parentId = parentBackStack.lastOrNull()?.id ?: 0L
        dataSource.invalidate()
    }

    fun createFolder(name: String) = viewModelScope.launch(defaultDispatcher) {
        actionUseCases.createFolderUseCase(name = name, parentId = parentId)
    }

    fun delete(ids: List<Long>) = viewModelScope.launch(defaultDispatcher.limitedParallelism(6)) {
        actionUseCases.deleteUseCase(ids)
    }

    fun move(ids: List<Long>) = viewModelScope.launch(defaultDispatcher) {
        actionUseCases.moveUseCase(ids = ids, parentId = parentId)
    }

    fun getCameraScanOutputUri(): Uri =
        filesUtil.getExportedCacheFileUri(file = filesUtil.getExportedCacheCameraFile())

    fun setStarred(state: Boolean, ids: List<Long>) = viewModelScope.launch(
        defaultDispatcher.limitedParallelism(6)
    ) {
        val itemState = if (state) ItemState.Starred else ItemState.Default
        actionUseCases.setStateUseCase(ids = ids, itemState = itemState)
    }

    fun import(
        vararg uriList: Uri,
        saveSource: Boolean = false,
        onSuccess: () -> Unit = {},
        onError: () -> Unit = {},
    ) = viewModelScope.launch(defaultDispatcher) {
        val listToSave = uriList.map { it.toString() }
        val fileWithUris = workerSerializer.saveStringListToFile(listToSave)
        val data = workDataOf(
            Args.URI_FILE to fileWithUris.toString(),
            Args.PARENT_ID to parentId,
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
                    onSuccess()
                    cancel()
                }

                WorkInfo.State.FAILED -> {
                    onError()
                    cancel()
                }

                else -> {}
            }
        }
    }

    fun rename(id: Long, newName: String) = viewModelScope.launch(defaultDispatcher) {
        actionUseCases.renameUseCase(id = id, newName = newName)
    }

    override fun onCleared() {
        state[ROOT_CURRENT] = parentId
        state[ROOT_BACK_STACK] = parentBackStack.toList()
    }

    init {
        val savedRootBackStack: List<RootInfo>? = state[ROOT_BACK_STACK]
        if (savedRootBackStack != null) {
            parentBackStackMutable.addAll(savedRootBackStack)
        }
        val savedCurrentRoot: Long? = state[ROOT_CURRENT]
        if (savedCurrentRoot != null) parentId = savedCurrentRoot
    }
}