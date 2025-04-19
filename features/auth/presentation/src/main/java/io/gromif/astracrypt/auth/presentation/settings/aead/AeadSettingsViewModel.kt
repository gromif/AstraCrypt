package io.gromif.astracrypt.auth.presentation.settings.aead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.auth.domain.model.AeadMode
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAeadModeFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.SetAeadModeUseCase
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
    private val setAeadModeUseCase: SetAeadModeUseCase,
    getAeadModeFlowUseCase: GetAeadModeFlowUseCase
): ViewModel() {
    val aeadModeFlow = getAeadModeFlowUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), AeadMode.None
    )

    fun setSettingsAead(aeadMode: AeadMode) = viewModelScope.launch(defaultDispatcher) {
        setAeadModeUseCase(aeadMode)
    }

}