package io.gromif.astracrypt.device_admin.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName

internal class DeviceAdminManager(
    private val devicePolicyManager: DevicePolicyManager,
    private val adminComponentImpl: ComponentName
) {
    val isActive get() = devicePolicyManager.isAdminActive(adminComponentImpl)

    fun disable() = devicePolicyManager.removeActiveAdmin(adminComponentImpl)
}