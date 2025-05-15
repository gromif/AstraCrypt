package io.gromif.astracrypt.files.settings.aead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.SetAeadInfoUseCase
import io.gromif.astracrypt.files.settings.aead.work.SetDatabaseAeadWorker
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AeadSettingsViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val workManager: WorkManager,
    private val setAeadInfoUseCase: SetAeadInfoUseCase,
    getAeadInfoFlowUseCase: GetAeadInfoFlowUseCase,
): ViewModel() {
    val aeadInfoState = getAeadInfoFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AeadInfo())

    fun setFileMode(aeadMode: AeadMode) = viewModelScope.launch(defaultDispatcher) {
        val newAeadInfo = aeadInfoState.value.copy(fileMode = aeadMode)
        setAeadInfoUseCase(newAeadInfo)
    }

    fun setPreviewMode(aeadMode: AeadMode) = viewModelScope.launch(defaultDispatcher) {
        val newAeadInfo = aeadInfoState.value.copy(previewMode = aeadMode)
        setAeadInfoUseCase(newAeadInfo)
    }

    fun setDatabaseMode(aeadMode: AeadMode) = viewModelScope.launch(defaultDispatcher) {
        val newAeadInfo = aeadInfoState.value.copy(databaseMode = aeadMode)
        val data = SetDatabaseAeadWorker.createWorkerData(targetAeadInfo = newAeadInfo)
        val workerRequest = OneTimeWorkRequestBuilder<SetDatabaseAeadWorker>().apply {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
    }

}