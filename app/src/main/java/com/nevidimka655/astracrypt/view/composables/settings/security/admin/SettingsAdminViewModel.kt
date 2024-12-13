package com.nevidimka655.astracrypt.view.composables.settings.security.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.utils.DeviceAdminManager
import com.nevidimka655.astracrypt.app.utils.contracts.RequestDeviceAdmin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsAdminViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val deviceAdminManager: DeviceAdminManager,
    val requestDeviceAdmin: RequestDeviceAdmin
) : ViewModel() {
    val isActive get() = deviceAdminManager.isActive
    fun disable() = viewModelScope.launch(defaultDispatcher) { deviceAdminManager.disable() }
}