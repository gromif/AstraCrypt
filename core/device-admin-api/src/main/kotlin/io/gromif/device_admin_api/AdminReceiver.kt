package io.gromif.device_admin_api

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class AdminReceiver: DeviceAdminReceiver() {

    internal companion object {
        private val statusChangedChannel = MutableStateFlow<Boolean?>(null)

        val receiverAdminStatusFlow = statusChangedChannel.asStateFlow()
    }

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        statusChangedChannel.value = true
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        statusChangedChannel.value = false
    }
}