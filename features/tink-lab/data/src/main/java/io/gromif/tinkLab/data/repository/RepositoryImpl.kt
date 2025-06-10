package io.gromif.tinkLab.data.repository

import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.tinkLab.domain.model.Repository

class RepositoryImpl : Repository {

    override fun getFileAeadList(): List<String> {
        return KeysetTemplates.Stream.entries
            .filter { it.name.endsWith("MB") }
            .map { it.name.removeSuffix("_1MB").lowercase() }
    }

    override fun getTextAeadList(): List<String> {
        return KeysetTemplates.AEAD.entries.map { it.name.lowercase() }
    }
}
