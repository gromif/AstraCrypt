package contract.auth

import kotlinx.coroutines.flow.Flow

interface AuthContract {

    suspend fun updateLastActiveTime()

    suspend fun verifyTimeout()

    fun getAuthStateFlow(): Flow<Boolean>

}