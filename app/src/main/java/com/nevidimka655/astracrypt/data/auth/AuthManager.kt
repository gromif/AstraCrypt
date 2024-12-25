package com.nevidimka655.astracrypt.data.auth

import com.nevidimka655.astracrypt.data.datastore.DefaultDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.domain.model.auth.Auth
import com.nevidimka655.astracrypt.domain.model.auth.AuthType
import com.nevidimka655.astracrypt.domain.model.auth.Skin
import com.nevidimka655.crypto.tink.core.hash.Sha384Util
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthManager(
    private val defaultDataStoreManager: DefaultDataStoreManager,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    private val sha384Util: Sha384Util
) {
    val infoFlow get() = settingsDataStoreManager.authFlow

    private suspend fun saveInfo(auth: Auth) {
        settingsDataStoreManager.setAuthInfo(auth)
    }

    suspend fun setHintState(state: Boolean) = saveInfo(infoFlow.first().copy(hintState = state))
    suspend fun setHintText(text: String) = saveInfo(infoFlow.first().copy(hintText = text))

    suspend fun setPassword(password: String) = coroutineScope {
        launch {
            defaultDataStoreManager.setPasswordHash(
                hash = sha384Util.compute(value = password.toByteArray())
            )
        }
        launch { saveInfo(auth = infoFlow.first().copy(type = AuthType.PASSWORD)) }
    }

    suspend fun verifyPassword(password: String) = coroutineScope {
        val currentHash = async { sha384Util.compute(value = password.toByteArray()) }
        val savedHash = async { defaultDataStoreManager.getPasswordHash() }
        currentHash.await().contentEquals(savedHash.await())
    }

    suspend fun disable() = coroutineScope {
        launch { defaultDataStoreManager.setPasswordHash(hash = null) }
        launch { saveInfo(auth = infoFlow.first().copy(type = null)) }
    }

    suspend fun setSkin(skin: Skin?) = saveInfo(
        auth = infoFlow.first().copy(skin = skin)
    )

}