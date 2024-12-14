package com.nevidimka655.astracrypt.app.utils

import com.nevidimka655.astracrypt.data.datastore.SettingsDataStoreManager
import com.nevidimka655.astracrypt.data.model.AeadInfo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AeadManager @Inject constructor(
    private val settingsDataStoreManager: SettingsDataStoreManager
) {
    private var info: AeadInfo? = null
    val infoFlow = settingsDataStoreManager.aeadInfoFlow

    private suspend fun saveInfo(aeadInfo: AeadInfo) =
        settingsDataStoreManager.setAeadInfo(aeadInfo)

    suspend fun setBindAssociatedData(state: Boolean) = saveInfo(
        aeadInfo = infoFlow.first().copy(bindAssociatedData = state)
    )

    suspend fun getInfo() = info ?: infoFlow.first()
        .also { info = it }

    fun getCachedInfo() = info

}