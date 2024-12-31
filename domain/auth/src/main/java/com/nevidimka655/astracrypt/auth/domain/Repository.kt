package com.nevidimka655.astracrypt.auth.domain

interface Repository {

    suspend fun setHintVisibility(visible: Boolean)

    suspend fun setHintText(text: String)

    suspend fun setPassword(password: String)

    suspend fun verifyPassword(password: String)

    suspend fun disable()

    suspend fun setSkin(skin: Skin?)

}