package com.nevidimka655.domain.notes.repository

interface SettingsRepository {

    suspend fun getAeadTemplateIndex(): Int

    suspend fun setAeadTemplateIndex(aead: Int)

}