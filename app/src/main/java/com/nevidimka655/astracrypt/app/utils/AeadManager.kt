package com.nevidimka655.astracrypt.app.utils

import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AeadManager @Inject constructor(
    private val settingsDataStoreManager: SettingsDataStoreManager
) {
    private var info: AeadInfo? = null

    suspend fun getInfo() = info ?: settingsDataStoreManager.aeadInfoFlow.first()
        .also { info = it }

    fun getCachedInfo() = info

}