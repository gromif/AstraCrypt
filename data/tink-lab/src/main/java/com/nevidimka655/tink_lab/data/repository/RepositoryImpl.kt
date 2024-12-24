package com.nevidimka655.tink_lab.data.repository

import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.tink_lab.domain.model.Repository

class RepositoryImpl: Repository {
    override fun getFileAeadList(): List<String> {
        return KeysetTemplates.Stream.entries
            .filter { it.name.endsWith("MB") }
            .map { it.name.removeSuffix("_1MB").lowercase() }
    }

    override fun getTextAeadList(): List<String> {
        return KeysetTemplates.AEAD.entries.map { it.name.lowercase() }
    }
}