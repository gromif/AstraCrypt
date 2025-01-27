package io.gromif.astracrypt.files.export

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.domain.usecase.PrivateExportUseCase
import io.gromif.astracrypt.files.model.ExportStateHolder
import io.gromif.astracrypt.files.work.ExportFilesWorker
import io.gromif.astracrypt.files.work.ExportFilesWorker.Args
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class ExportScreenViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val filesUtil: FilesUtil,
    private val workManager: WorkManager,
    private val privateExportUseCase: PrivateExportUseCase,
    private val uriMapper: Mapper<String, Uri>
) : ViewModel() {
    private val workUUID = UUID.randomUUID()
    var internalExportUri: Uri = Uri.EMPTY
    var uiState by mutableStateOf(ExportStateHolder())

    fun export(id: Long) = viewModelScope.launch(defaultDispatcher) {
        privateExportUseCase(id)?.let {
            internalExportUri = uriMapper(it)
        }
        uiState = uiState.copy(isDone = true)
    }

    fun export(idList: Array<Long>, output: String) = viewModelScope.launch(defaultDispatcher) {
        val data = workDataOf(
            Args.ID_LIST to idList,
            Args.URI_TARGET to output
        )
        val workerRequest = OneTimeWorkRequestBuilder<ExportFilesWorker>().apply {
            setId(workUUID)
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
    }

    fun observeWorkInfoState() = viewModelScope.launch(defaultDispatcher) {
        workManager.getWorkInfoByIdFlow(id = workUUID).stateIn(this).collectLatest {
            it?.let { workInfo ->
                if (workInfo.state.isFinished) {
                    uiState = uiState.copy(isDone = true)
                    cancel()
                }
            }
        }
    }

    fun cancelExport() = workManager.cancelWorkById(id = workUUID)

    fun onDispose() = filesUtil.clearExportedCache()

}