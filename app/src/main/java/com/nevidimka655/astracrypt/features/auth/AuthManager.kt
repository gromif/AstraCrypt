package com.nevidimka655.astracrypt.features.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import com.google.crypto.tink.subtle.AesGcmJce
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.crypto.tink.HashStringGenerator
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.sha384
import com.nevidimka655.crypto.tink.extensions.toBase64
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsKeys
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsManager
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.security.GeneralSecurityException
import kotlin.random.Random

class AuthManager {
    private val cryptoPrefs get() = PrefsManager.settings
    private val masterPrefs get() = PrefsManager.clear

    var info by mutableStateOf(loadInfo())
        private set

    private fun loadInfo() = run {
        val info = cryptoPrefs.getString(PrefsKeys.Auth.INFO, "")!!
        val infoObj = if (info.isEmpty()) AuthInfo() else Json.decodeFromString(info)
        infoObj
    }

    fun saveInfo(authInfo: AuthInfo) {
        info = authInfo
        cryptoPrefs.putString(
            key = PrefsKeys.Auth.INFO,
            value = Json.encodeToString(authInfo)
        ).commit()
    }

    fun changePassword(password: String) {
        val key = extendStringPassword(password)
        val associatedData = password.sha384()
        val randomBytesToEncrypt = Random.nextBytes(Random.nextInt(20, 51))
        val primitive = AesGcmJce(key)
        val encryptedData = primitive.encrypt(randomBytesToEncrypt, associatedData).toBase64()
        masterPrefs.edit {
            putString(PrefsKeys.Auth.TEST_DATA, encryptedData)
        }
    }

    fun checkPassword(password: String): Boolean {
        val key = extendStringPassword(password)
        val associatedData = password.sha384()
        val primitive = AesGcmJce(key)
        return try {
            val testData = masterPrefs.getString(PrefsKeys.Auth.TEST_DATA, null)!!
                .fromBase64()
            primitive.decrypt(testData, associatedData)
            true
        } catch (e: GeneralSecurityException) {
            false
        }
    }

    private fun extendStringPassword(password: String) = HashStringGenerator.extendString(
        string = password,
        targetSize = AppConfig.AUTH_PASSWORD_HASH_LENGTH
    )

}