package io.gromif.astracrypt.device_admin.utils

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

internal class DeviceAdminReceiverImpl : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
    }

}