package com.nevidimka655.astracrypt.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import com.nevidimka655.astracrypt.utils.extensions.devicePolicyManager
import com.nevidimka655.astracrypt.utils.receivers.DeviceAdminImpl

object DeviceAdminManager {

    fun getComponentName(context: Context) = ComponentName(context, DeviceAdminImpl::class.java)

    fun init(context: Context) = context.devicePolicyManager()

    fun isAdminRightsGranted(context: Context, devicePolicyManager: DevicePolicyManager) =
        devicePolicyManager.isAdminActive(getComponentName(context))

    fun revokeAdminRights(context: Context, devicePolicyManager: DevicePolicyManager) =
        devicePolicyManager.removeActiveAdmin(getComponentName(context))

}