package com.nevidimka655.astracrypt.auth.domain.service

interface TinkService {

    suspend fun enableAssociatedDataBind(password: String)

    suspend fun disableAssociatedDataBind()

    suspend fun decryptAssociatedData(password: String)

    suspend fun computeAuthHash(data: String): ByteArray

    suspend fun computeSkinHash(data: String): ByteArray

}