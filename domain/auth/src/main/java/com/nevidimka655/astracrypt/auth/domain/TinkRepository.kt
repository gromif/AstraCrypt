package com.nevidimka655.astracrypt.auth.domain

interface TinkRepository {

    suspend fun enableAssociatedDataBind(password: String)

    suspend fun disableAssociatedDataBind()

    suspend fun computeAuthHash(data: String): ByteArray

    suspend fun computeSkinHash(data: String): ByteArray

}