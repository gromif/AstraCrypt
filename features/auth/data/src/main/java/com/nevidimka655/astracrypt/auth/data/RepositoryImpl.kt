package com.nevidimka655.astracrypt.auth.data

import com.nevidimka655.astracrypt.auth.data.datastore.AuthDataStoreManager
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.Repository
import com.nevidimka655.astracrypt.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val authDataStoreManager: AuthDataStoreManager,
    private val authToAuthDtoMapper: Mapper<Auth, AuthDto>,
    private val authDtoToAuthMapper: Mapper<AuthDto, Auth>
) : Repository {

    override suspend fun setAuth(auth: Auth) {
        val authDto = authToAuthDtoMapper(auth)
        authDataStoreManager.setAuthInfo(authDto = authDto)
    }

    override fun getAuthFlow(): Flow<Auth> {
        return authDataStoreManager.authFlow.map { authDtoToAuthMapper(it) }
    }

    override suspend fun setHintVisibility(auth: Auth, visible: Boolean) {
        setAuth(auth.copy(hintState = visible))
    }

    override suspend fun setHintText(auth: Auth, text: String) {
        setAuth(auth.copy(hintText = text))
    }

    override suspend fun setBindTinkAd(auth: Auth, bind: Boolean) {
        setAuth(auth.copy(bindTinkAd = bind))
    }

    override suspend fun setAuthHash(hash: ByteArray?) {
        authDataStoreManager.setAuthHash(hash)
    }

    override suspend fun getAuthHash(): ByteArray {
        return authDataStoreManager.getAuthHash()
    }

    override suspend fun setSkinHash(hash: ByteArray?) {
        authDataStoreManager.setSkinHash(hash = hash)
    }

    override suspend fun getSkinHash(): ByteArray {
        return authDataStoreManager.getSkinHash()
    }

}