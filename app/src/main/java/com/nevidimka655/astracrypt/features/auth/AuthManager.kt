package com.nevidimka655.astracrypt.features.auth

import com.nevidimka655.astracrypt.data.datastore.DefaultDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.features.auth.model.AuthInfo
import com.nevidimka655.astracrypt.features.auth.model.AuthType
import com.nevidimka655.astracrypt.features.auth.model.Skin
import com.nevidimka655.crypto.tink.extensions.sha384
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthManager(
    private val defaultDataStoreManager: DefaultDataStoreManager,
    private val settingsDataStoreManager: SettingsDataStoreManager
) {
    val infoFlow get() = settingsDataStoreManager.authInfoFlow

    private suspend fun saveInfo(authInfo: AuthInfo) {
        settingsDataStoreManager.setAuthInfo(authInfo)
    }

    suspend fun setHintState(state: Boolean) = saveInfo(infoFlow.first().copy(hintState = state))
    suspend fun setHintText(text: String) = saveInfo(infoFlow.first().copy(hintText = text))

    suspend fun setPassword(password: String) = coroutineScope {
        launch { defaultDataStoreManager.setPasswordHash(hash = password.sha384()) }
        launch { saveInfo(authInfo = infoFlow.first().copy(type = AuthType.PASSWORD)) }
    }

    suspend fun verifyPassword(password: String) = coroutineScope {
        val currentHash = async { password.sha384() }
        val savedHash = async { defaultDataStoreManager.getPasswordHash() }
        currentHash.await().contentEquals(savedHash.await())
    }

    suspend fun disable() = coroutineScope {
        launch { defaultDataStoreManager.setPasswordHash(hash = null) }
        launch { saveInfo(authInfo = infoFlow.first().copy(type = null)) }
    }

    suspend fun setSkin(skin: Skin?) = saveInfo(
        authInfo = infoFlow.first().copy(skin = skin)
    )

}