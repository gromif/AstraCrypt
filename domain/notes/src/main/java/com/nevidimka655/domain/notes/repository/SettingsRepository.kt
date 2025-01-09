package com.nevidimka655.domain.notes.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAeadTemplateIndexFlow(): Flow<Int>

    suspend fun getAeadTemplateIndex(): Int

    suspend fun setAeadTemplateIndex(aead: Int)

}