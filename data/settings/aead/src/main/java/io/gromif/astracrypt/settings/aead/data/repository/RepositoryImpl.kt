package io.gromif.astracrypt.settings.aead.data.repository

import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.domain.notes.repository.SettingsRepository
import io.gromif.astracrypt.settings.aead.domain.model.AeadTemplate
import io.gromif.astracrypt.settings.aead.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val notesSettingsRepository: NotesSettingsRepository
): Repository {

    override suspend fun getNotesAeadName(): Flow<AeadTemplate> {
        return notesSettingsRepository.getAeadTemplateIndexFlow().map {
            AeadTemplate(id = it, name = KeysetTemplates.AEAD.entries[it].name)
        }
    }

    override fun getAeadTemplateList(): List<AeadTemplate> {
        val aeadEntries = KeysetTemplates.AEAD.entries
        return aeadEntries.map {
            AeadTemplate(id = it.ordinal, name = it.name.lowercase())
        }
    }

    override fun getAeadLargeStreamTemplateList(): List<AeadTemplate> {
        val streamEntries = KeysetTemplates.Stream.entries
        val suffix = "1MB"
        return streamEntries.filter { it.name.endsWith(suffix) }.map {
            AeadTemplate(id = it.ordinal, name = it.name.removeSuffix(suffix).lowercase())
        }
    }

    override fun getAeadSmallStreamTemplateList(): List<AeadTemplate> {
        val streamEntries = KeysetTemplates.Stream.entries
        val suffix = "4KB"
        return streamEntries.filter { it.name.endsWith(suffix) }.map {
            AeadTemplate(id = it.ordinal, name = it.name.removeSuffix(suffix).lowercase())
        }
    }

}

typealias NotesSettingsRepository = SettingsRepository