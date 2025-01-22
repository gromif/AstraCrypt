package io.gromif.astracrypt.settings.aead.data.repository

import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import io.gromif.astracrypt.settings.aead.domain.model.AeadTemplate
import io.gromif.astracrypt.settings.aead.domain.repository.Repository
import io.gromif.astracrypt.settings.aead.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val settingsRepository: SettingsRepository,
    private val notesSettingsRepository: NotesSettingsRepository
): Repository {

    override fun getNotesAeadNameFlow(): Flow<String?> {
        return notesSettingsRepository.getAeadTemplateIndexFlow().map {
            KeysetTemplates.AEAD.entries.getOrNull(it)?.name
        }
    }

    override suspend fun setNotesAeadTemplateIndex(aead: Int) {
        notesSettingsRepository.setAeadTemplateIndex(aead = aead)
    }

    override fun getAeadTemplateList(): List<AeadTemplate> {
        val aeadEntries = KeysetTemplates.AEAD.entries
        return aeadEntries.map {
            AeadTemplate(id = it.ordinal, name = it.name.lowercase())
        }
    }

    override fun getAeadLargeStreamTemplateList(): List<AeadTemplate> {
        val streamEntries = KeysetTemplates.Stream.entries
        val suffix = "_1MB"
        return streamEntries.filter { it.name.endsWith(suffix) }.map {
            AeadTemplate(id = it.ordinal, name = it.name.removeSuffix(suffix).lowercase())
        }
    }

    override fun getAeadSmallStreamTemplateList(): List<AeadTemplate> {
        val streamEntries = KeysetTemplates.Stream.entries
        val suffix = "_4KB"
        return streamEntries.filter { it.name.endsWith(suffix) }.map {
            AeadTemplate(id = it.ordinal, name = it.name.removeSuffix(suffix).lowercase())
        }
    }

}

typealias NotesSettingsRepository = com.nevidimka655.domain.notes.repository.SettingsRepository