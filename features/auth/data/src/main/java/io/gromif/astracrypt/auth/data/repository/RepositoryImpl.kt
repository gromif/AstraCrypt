package io.gromif.astracrypt.auth.data.repository

import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.service.TinkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

private const val SECOND_MILLIS = 1000

class RepositoryImpl(
    private val tinkService: TinkService
) : Repository {
    private val authValidMutableState = MutableStateFlow(false)
    private val skinValidMutableState = MutableStateFlow(false)

    override fun getAuthStateFlow(): Flow<Boolean> = authValidMutableState
    override fun getSkinStateFlow(): Flow<Boolean> = skinValidMutableState

    override fun verifyTimeout(
        currentTime: Long,
        lastActiveTime: Long,
        timeout: Timeout
    ) {
        val timeoutMillis = timeout.seconds * SECOND_MILLIS

        if (timeout != Timeout.NEVER) {
            val verificationResult = currentTime - lastActiveTime < timeoutMillis
            if (!verificationResult) {
                authValidMutableState.update { false }
                skinValidMutableState.update { false }
            }
        }
    }

    override suspend fun verifyAuth(
        savedHash: ByteArray,
        secret: String
    ): Boolean {
        val secretHash = tinkService.computeAuthHash(data = secret)
        return authValidMutableState.updateAndGet {
            secretHash.contentEquals(savedHash)
        }
    }

    override suspend fun verifySkin(
        savedHash: ByteArray,
        secret: String
    ): Boolean {
        val secretHash = tinkService.computeSkinHash(data = secret)
        return skinValidMutableState.updateAndGet {
            secretHash.contentEquals(savedHash)
        }
    }
}
