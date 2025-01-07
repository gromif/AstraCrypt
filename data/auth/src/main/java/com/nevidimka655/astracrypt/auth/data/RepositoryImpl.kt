package com.nevidimka655.astracrypt.auth.data

import com.nevidimka655.astracrypt.auth.data.datastore.AuthDataStoreManager
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.AuthType
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.utils.Mapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val authDataStoreManager: AuthDataStoreManager,
    private val authToAuthDtoMapper: Mapper<Auth, AuthDto>,
    private val authDtoToAuthMapper: Mapper<AuthDto, Auth>
) : Repository {

    private suspend fun saveInfo(authDto: AuthDto) {
        authDataStoreManager.setAuthInfo(authDto = authDto)
    }

    override fun getAuthFlow(): Flow<Auth> {
        return authDataStoreManager.authFlow.map { authDtoToAuthMapper(it) }
    }

    override suspend fun setHintVisibility(auth: Auth, visible: Boolean) {
        val authDto = authToAuthDtoMapper(auth).copy(hintState = visible)
        saveInfo(authDto = authDto)
    }

    override suspend fun setHintText(auth: Auth, text: String) {
        val authDto = authToAuthDtoMapper(auth).copy(hintText = text)
        saveInfo(authDto = authDto)
    }

    override suspend fun setAuth(auth: Auth) {
        val authDto = authToAuthDtoMapper(auth)
        authDataStoreManager.setAuthInfo(authDto = authDto)
    }

    override suspend fun setBindTinkAd(auth: Auth, bind: Boolean) {
        val authDto = authToAuthDtoMapper(auth).copy(bindTinkAd = bind)
        saveInfo(authDto = authDto)
    }

    override suspend fun disable(auth: Auth): Unit = coroutineScope {
        launch { authDataStoreManager.setPasswordHash(hash = null) }
        launch { saveInfo(authDto = authToAuthDtoMapper(auth).copy(type = -1)) }
    }

    override suspend fun setAuthHash(hash: ByteArray?) {
        authDataStoreManager.setPasswordHash(hash)
    }

    override suspend fun getAuthHash(): ByteArray {
        return authDataStoreManager.getPasswordHash()
    }

    override suspend fun setSkinHash(hash: ByteArray?) {
        TODO("Not yet implemented")
    }

    override suspend fun getSkinHash(): ByteArray {
        TODO("Not yet implemented")
    }

    override suspend fun setSkinDefault(auth: Auth) {
        val authDto = authToAuthDtoMapper(auth.copy(skin = null))
        saveInfo(authDto = authDto)
    }

}