package io.gromif.astracrypt.files.settings.aead.columns

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.usecase.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.settings.aead.work.SetDatabaseAeadWorker
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ColumnsAeadSettingsViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val workManager: WorkManager,
    getAeadInfoFlowUseCase: GetAeadInfoFlowUseCase,
) : ViewModel() {
    var name by mutableStateOf(false)
    var preview by mutableStateOf(false)
    var file by mutableStateOf(false)
    var flag by mutableStateOf(false)

    val aeadInfoState = getAeadInfoFlowUseCase().onEach {
        name = it.name
        preview = it.preview
        file = it.file
        flag = it.flag
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AeadInfo())

    fun setColumnsAeadSettings() = viewModelScope.launch(defaultDispatcher) {
        val newAeadInfo = aeadInfoState.value.copy(
            name = name,
            preview = preview,
            file = file,
            flag = flag
        )
        val data = SetDatabaseAeadWorker.createWorkerData(targetAeadInfo = newAeadInfo)
        val workerRequest = OneTimeWorkRequestBuilder<SetDatabaseAeadWorker>().apply {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
    }

}