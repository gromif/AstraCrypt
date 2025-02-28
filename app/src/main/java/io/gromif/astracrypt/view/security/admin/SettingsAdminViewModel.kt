package io.gromif.astracrypt.view.security.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.app.device_admin.DeviceAdminManager
import io.gromif.astracrypt.app.device_admin.RequestDeviceAdminContract
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsAdminViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val deviceAdminManager: DeviceAdminManager,
    val requestDeviceAdminContract: RequestDeviceAdminContract
) : ViewModel() {
    val isActive get() = deviceAdminManager.isActive
    fun disable() = viewModelScope.launch(defaultDispatcher) { deviceAdminManager.disable() }
}