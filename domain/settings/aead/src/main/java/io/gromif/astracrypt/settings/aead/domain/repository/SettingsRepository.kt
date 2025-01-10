package io.gromif.astracrypt.settings.aead.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAeadNameFlow(): Flow<String?>

    suspend fun getAeadTemplateIndex(): Int

    suspend fun setAeadTemplateIndex(aead: Int)

}