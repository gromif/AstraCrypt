package io.gromif.astracrypt.settings.aead.domain.repository

import io.gromif.astracrypt.settings.aead.domain.model.AeadTemplate
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getNotesAeadTemplateFlow(): Flow<AeadTemplate>

    fun getAeadTemplateList(): List<AeadTemplate>

    fun getAeadLargeStreamTemplateList(): List<AeadTemplate>

    fun getAeadSmallStreamTemplateList(): List<AeadTemplate>

}