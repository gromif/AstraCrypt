package io.gromif.astracrypt.app.device_admin

import android.app.admin.DevicePolicyManager
import android.content.ComponentName

class DeviceAdminManager(
    private val devicePolicyManager: DevicePolicyManager,
    private val adminComponentImpl: ComponentName
) {
    val isActive get() = devicePolicyManager.isAdminActive(adminComponentImpl)

    fun disable() = devicePolicyManager.removeActiveAdmin(adminComponentImpl)
}