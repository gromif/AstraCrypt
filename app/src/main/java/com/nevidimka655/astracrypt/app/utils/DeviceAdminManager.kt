package com.nevidimka655.astracrypt.app.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName

class DeviceAdminManager(
    private val devicePolicyManager: DevicePolicyManager,
    private val adminComponentImpl: ComponentName
) {
    val isActive get() = devicePolicyManager.isAdminActive(adminComponentImpl)

    fun disable() = devicePolicyManager.removeActiveAdmin(adminComponentImpl)
}