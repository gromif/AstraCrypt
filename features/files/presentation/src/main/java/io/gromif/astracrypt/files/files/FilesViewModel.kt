package io.gromif.astracrypt.files.files

import android.net.Uri
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
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.preferences.GetListViewModeUseCase
import io.gromif.astracrypt.files.files.util.ActionUseCases
import io.gromif.astracrypt.files.files.util.DataUseCases
import io.gromif.astracrypt.files.files.util.NavigatorUseCases
import io.gromif.astracrypt.files.work.ImportFilesWorker
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.astracrypt.utils.io.FilesUtil
import io.gromif.astracrypt.utils.io.WorkerSerializer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private typealias Args = ImportFilesWorker.Args

@HiltViewModel
internal class FilesViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val dataUseCases: DataUseCases<PagingData<Item>>,
    private val navigatorUseCases: NavigatorUseCases,
    private val actionUseCases: ActionUseCases,
    private val workManager: WorkManager,
    private val workerSerializer: WorkerSerializer,
    val filesUtil: FilesUtil,
    @FilesImageLoader
    val imageLoader: ImageLoader,
    getListViewModeUseCase: GetListViewModeUseCase,
    getValidationRulesUsecase: GetValidationRulesUseCase,
) : ViewModel() {
    val navigationBackStackState = navigatorUseCases.getNavBackStackFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    val pagingFlow = dataUseCases.getFilesDataFlow().cachedIn(viewModelScope)
    val pagingStarredFlow = dataUseCases.getStarredDataFlow().cachedIn(viewModelScope)

    val validationRules = getValidationRulesUsecase()
    val viewModeState = getListViewModeUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ViewMode.Grid)

    fun setSearchQuery(query: String) {
        dataUseCases.requestSearchUseCase(query)
    }

    fun openDirectory(id: Long, name: String) = viewModelScope.launch(defaultDispatcher) {
        navigatorUseCases.openNavFolderUseCase(id, name)
    }

    fun openRootDirectory() = viewModelScope.launch(defaultDispatcher) {
        navigatorUseCases.resetNavBackStackUseCase()
    }

    fun closeDirectory() {
        navigatorUseCases.closeNavFolderUseCase()
    }

    fun createFolder(name: String) = viewModelScope.launch(defaultDispatcher) {
        actionUseCases.createFolderUseCase(name = name)
    }

    fun delete(ids: List<Long>) = viewModelScope.launch(defaultDispatcher.limitedParallelism(6)) {
        actionUseCases.deleteUseCase(ids)
    }

    fun move(ids: List<Long>) = viewModelScope.launch(defaultDispatcher) {
        actionUseCases.moveUseCase(ids = ids)
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
        val folderId = navigatorUseCases.getCurrentNavFolderUseCase().id
        val data = workDataOf(
            Args.URI_FILE to fileWithUris.toString(),
            Args.PARENT_ID to folderId,
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

}