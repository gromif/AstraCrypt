package com.nevidimka655.astracrypt.utils.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.crypto.tink.CryptoPreferences
import com.nevidimka655.crypto.tink.KeysetTemplates

object PrefsManager {
    private var _clear: SharedPreferences? = null
    val clear get() = _clear ?: loadDefaultClearPrefs()

    private var _settings: CryptoPreferences? = null
    val settings get() = _settings ?: run {
        var isEncryptionEnabled = true
        val savedSettingsEncryption = clear.getInt(PrefsKeys.ENCRYPTION_SETTINGS, -1)
        val aeadTemplate = if (savedSettingsEncryption > -1) {
            KeysetTemplates.AEAD.entries[savedSettingsEncryption]
        } else {
            isEncryptionEnabled = false
            KeysetTemplates.AEAD.AES128_GCM
        }
        CryptoPreferences(
            context = Engine.appContext,
            prefsName = PrefsKeys.FileNames.SETTINGS,
            aeadKeyTemplate = aeadTemplate,
            isEncryptionEnabled = isEncryptionEnabled
        ).also { _settings = it }
    }

    fun loadDefaultClearPrefs() = loadClearPrefs(PrefsKeys.FileNames.MASTER)

    fun loadClearPrefs(name: String) = Engine.appContext.getSharedPreferences(
        name,
        Context.MODE_PRIVATE
    )!!.also { _clear = it }

    fun clearAllPrefs() {
        clear.edit(true) {
            clear()
        }
        with(settings.prefsEditor) {
            clear()
            commit()
        }
    }

}