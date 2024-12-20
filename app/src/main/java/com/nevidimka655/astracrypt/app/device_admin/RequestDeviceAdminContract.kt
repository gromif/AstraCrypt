package com.nevidimka655.astracrypt.app.device_admin

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.nevidimka655.astracrypt.R

class RequestDeviceAdminContract(
    private val adminComponentImpl: ComponentName
): ActivityResultContract<Void?, Boolean>() {

    override fun createIntent(context: Context, input: Void?) = Intent(
        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
    ).apply {
        putExtra(
            DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponentImpl
        )
        putExtra(
            DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            context.getString(R.string.settings_deviceAdminRights_device_summary)
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?) =
        resultCode == Activity.RESULT_OK
}