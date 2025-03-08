package io.gromif.astracrypt.device_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.device_admin.utils.DeviceAdminManager
import io.gromif.astracrypt.device_admin.utils.RequestDeviceAdminContract
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsAdminViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val deviceAdminManager: DeviceAdminManager,
    val requestDeviceAdminContract: RequestDeviceAdminContract
) : ViewModel() {
    val isActive get() = deviceAdminManager.isActive
    fun disable() = viewModelScope.launch(defaultDispatcher) { deviceAdminManager.disable() }
}