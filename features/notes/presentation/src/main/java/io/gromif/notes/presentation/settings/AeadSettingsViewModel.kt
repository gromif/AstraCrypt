package io.gromif.notes.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.crypto.tink.core.extensions.getTemplateList
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.usecase.GetAeadPreferenceFlowUseCase
import io.gromif.notes.presentation.settings.work.UpdateAeadWorker
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
    getAeadPreferenceFlowUseCase: GetAeadPreferenceFlowUseCase,
) : ViewModel() {
    val aeadTemplatesList = KeysetTemplates.AEAD.getTemplateList()
    val aeadPreference = getAeadPreferenceFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun setAeadPreference(targetAead: AeadMode) = viewModelScope.launch(defaultDispatcher) {
        val data = UpdateAeadWorker.Companion.createWorkerData(targetAead = targetAead)
        val workerRequest = OneTimeWorkRequestBuilder<UpdateAeadWorker>().apply {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
    }

}