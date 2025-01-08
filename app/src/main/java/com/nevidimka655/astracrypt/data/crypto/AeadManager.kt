package com.nevidimka655.astracrypt.data.crypto

import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.model.AeadInfo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AeadManager @Inject constructor(
    settingsDataStoreManager: SettingsDataStoreManager
) {
    private var info: AeadInfo? = null
    private val infoFlow = settingsDataStoreManager.aeadInfoFlow

    suspend fun getInfo() = info ?: infoFlow.first()
        .also { info = it }

}