package io.gromif.device_admin_api

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AndroidDeviceAdminApi(
    private val dpm: DevicePolicyManager,
    private val admin: ComponentName,
    private val packageName: String
) : DeviceAdminApi {
    private val mutex = Mutex()
    private val mutableTypeStateFlow = MutableStateFlow<DeviceAdminApi.Type?>(null)
    private val typeFlow = mutableTypeStateFlow.onSubscription { updateType() }
        .filterNotNull()
        .combine(AdminReceiver.receiverAdminStatusFlow) { type, receiverStatus ->
            if (receiverStatus != null && !receiverStatus)
                DeviceAdminApi.Type.NONE.also { updateType() }
            else type
        }

    private suspend fun updateType() {
        val currentType = when {
            hasAdminRights() -> DeviceAdminApi.Type.ADMIN
            hasDeviceRights() -> DeviceAdminApi.Type.DEVICE_OWNER
            hasProfileRights() -> DeviceAdminApi.Type.PROFILE_OWNER
            else -> DeviceAdminApi.Type.NONE
        }
        mutableTypeStateFlow.update { currentType }
    }

    override fun getTypeFlow(): Flow<DeviceAdminApi.Type> = typeFlow

    override suspend fun hasAdminRights() = mutex.withLock { dpm.isAdminActive(admin) }
    override suspend fun hasProfileRights() = mutex.withLock { dpm.isProfileOwnerApp(packageName) }
    override suspend fun hasDeviceRights() = mutex.withLock { dpm.isDeviceOwnerApp(packageName) }

    override suspend fun revokeAdminRights() = mutex.withLock { dpm.removeActiveAdmin(admin) }

}