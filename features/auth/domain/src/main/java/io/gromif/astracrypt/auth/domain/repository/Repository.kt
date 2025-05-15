package io.gromif.astracrypt.auth.domain.repository

import io.gromif.astracrypt.auth.domain.model.Timeout
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAuthStateFlow(): Flow<Boolean>

    fun getSkinStateFlow(): Flow<Boolean>

    fun verifyTimeout(
        currentTime: Long,
        lastActiveTime: Long,
        timeout: Timeout
    )

    suspend fun verifyAuth(savedHash: ByteArray, secret: String): Boolean

    suspend fun verifySkin(savedHash: ByteArray, secret: String): Boolean
}
