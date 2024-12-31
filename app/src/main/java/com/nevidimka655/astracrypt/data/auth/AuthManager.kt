package com.nevidimka655.astracrypt.data.auth

import com.nevidimka655.astracrypt.data.datastore.DefaultDataStoreManager
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.AuthType
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.Skin
import com.nevidimka655.crypto.tink.core.hash.Sha384Util
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthManager(
    private val defaultDataStoreManager: DefaultDataStoreManager,
    private val settingsDataStoreManager: SettingsDataStoreManager,
    private val sha384Util: Sha384Util
): Repository {
    val infoFlow get() = settingsDataStoreManager.authFlow

    private suspend fun saveInfo(auth: Auth) {
        settingsDataStoreManager.setAuthInfo(auth)
    }

    override suspend fun setHintVisibility(visible: Boolean) {
        saveInfo(infoFlow.first().copy(hintState = visible))
    }

    override suspend fun setHintText(text: String) {
        saveInfo(infoFlow.first().copy(hintText = text))
    }

    override suspend fun setPassword(password: String): Unit = coroutineScope {
        launch {
            defaultDataStoreManager.setPasswordHash(
                hash = sha384Util.compute(value = password.toByteArray())
            )
        }
        launch { saveInfo(auth = infoFlow.first().copy(type = AuthType.PASSWORD)) }
    }

    override suspend fun verifyPassword(password: String): Unit = coroutineScope {
        val currentHash = async { sha384Util.compute(value = password.toByteArray()) }
        val savedHash = async { defaultDataStoreManager.getPasswordHash() }
        currentHash.await().contentEquals(savedHash.await())
    }

    override suspend fun disable(): Unit = coroutineScope {
        launch { defaultDataStoreManager.setPasswordHash(hash = null) }
        launch { saveInfo(auth = infoFlow.first().copy(type = null)) }
    }

    override suspend fun setSkin(skin: Skin?) {
        saveInfo(auth = infoFlow.first().copy(skin = skin))
    }

}