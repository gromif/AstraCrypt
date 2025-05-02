package io.gromif.device_admin_api

import kotlinx.coroutines.flow.Flow

interface DeviceAdminApi {

    fun getTypeFlow(): Flow<Type>

    suspend fun hasAdminRights(): Boolean

    suspend fun hasProfileRights(): Boolean

    suspend fun hasDeviceRights(): Boolean

    suspend fun revokeAdminRights()

    enum class Type {
        NONE, ADMIN, PROFILE_OWNER, DEVICE_OWNER
    }

}