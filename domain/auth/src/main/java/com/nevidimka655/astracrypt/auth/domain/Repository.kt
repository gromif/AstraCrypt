package com.nevidimka655.astracrypt.auth.domain

import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAuthFlow(): Flow<Auth>

    suspend fun setHintVisibility(auth: Auth, visible: Boolean)

    suspend fun setHintText(auth: Auth, text: String)

    suspend fun setAuth(auth: Auth)

    suspend fun setBindTinkAd(auth: Auth, bind: Boolean)

    suspend fun setAuthHash(hash: ByteArray?)
    suspend fun getAuthHash(): ByteArray

    suspend fun setSkinHash(hash: ByteArray?)
    suspend fun getSkinHash(): ByteArray

}