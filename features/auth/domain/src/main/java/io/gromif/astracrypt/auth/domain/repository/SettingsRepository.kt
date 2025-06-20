package io.gromif.astracrypt.auth.domain.repository

import io.gromif.astracrypt.auth.domain.model.AeadMode
import io.gromif.astracrypt.auth.domain.model.Auth
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val authFlow: Flow<Auth>

    suspend fun setAuth(auth: Auth)
    suspend fun getAuth(): Auth

    suspend fun getAuthHash(): ByteArray
    suspend fun setAuthHash(hash: ByteArray?)

    suspend fun getSkinHash(): ByteArray
    suspend fun setSkinHash(hash: ByteArray?)

    suspend fun setAead(aeadMode: AeadMode)
    fun getAeadFlow(): Flow<AeadMode>
}
