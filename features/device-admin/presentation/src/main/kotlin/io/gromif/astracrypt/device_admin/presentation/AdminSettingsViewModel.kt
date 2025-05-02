package io.gromif.astracrypt.device_admin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.device_admin.domain.usecase.GetAdminStateFlowUseCase
import io.gromif.astracrypt.device_admin.domain.usecase.RevokeAdminUseCase
import io.gromif.astracrypt.device_admin.presentation.contracts.RequestDeviceAdminContract
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AdminSettingsViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val revokeAdminUseCase: RevokeAdminUseCase,
    val requestDeviceAdminContract: RequestDeviceAdminContract,
    getAdminStateFlowUseCase: GetAdminStateFlowUseCase
) : ViewModel() {
    val adminState = getAdminStateFlowUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun disable() = viewModelScope.launch(defaultDispatcher) {
        revokeAdminUseCase()
    }
}