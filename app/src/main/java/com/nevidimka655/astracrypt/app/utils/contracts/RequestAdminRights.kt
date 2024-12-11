package com.nevidimka655.astracrypt.app.utils.contracts

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.utils.DeviceAdminManager

class RequestAdminRights : ActivityResultContract<Void?, Boolean>() {

    override fun createIntent(context: Context, input: Void?) = Intent(
        DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
    ).apply {
        putExtra(
            DevicePolicyManager.EXTRA_DEVICE_ADMIN,
            DeviceAdminManager.getComponentName(context)
        )
        putExtra(
            DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            context.getString(R.string.settings_deviceAdminRights_device_summary)
        )
    }

    override fun parseResult(resultCode: Int, intent: Intent?) =
        resultCode == Activity.RESULT_OK
}