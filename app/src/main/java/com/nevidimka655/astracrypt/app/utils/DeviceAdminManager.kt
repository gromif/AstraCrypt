package com.nevidimka655.astracrypt.app.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.nevidimka655.astracrypt.app.extensions.devicePolicyManager
import com.nevidimka655.astracrypt.app.utils.receivers.DeviceAdminImpl

object DeviceAdminManager {

    fun getComponentName(context: Context) = ComponentName(context, DeviceAdminImpl::class.java)

    fun init(context: Context) = context.devicePolicyManager()

    fun isAdminRightsGranted(context: Context, devicePolicyManager: DevicePolicyManager) =
        devicePolicyManager.isAdminActive(getComponentName(context))

    fun revokeAdminRights(context: Context, devicePolicyManager: DevicePolicyManager) =
        devicePolicyManager.removeActiveAdmin(getComponentName(context))

}