package com.nevidimka655.astracrypt.auth.domain

interface TinkRepository {

    suspend fun enableAssociatedDataBind(password: String)

    suspend fun disableAssociatedDataBind()

}