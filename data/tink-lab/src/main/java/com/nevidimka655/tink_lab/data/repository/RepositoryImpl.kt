package com.nevidimka655.tink_lab.data.repository

import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.util.KeyGenerator
import com.nevidimka655.tink_lab.domain.util.KeyReader
import com.nevidimka655.tink_lab.domain.util.KeyWriter

private val keysetAssociatedData = "labKey".toByteArray()

class RepositoryImpl(
    private val keyGenerator: KeyGenerator,
    private val keyWriter: KeyWriter,
    private val keyReader: KeyReader
) : Repository {
    override fun createKey(dataType: DataType, aeadType: String): Key {
        return keyGenerator(dataType = dataType, aeadType = aeadType)
    }

    override fun save(key: Key, uriString: String, keysetPassword: String) {
        keyWriter(
            uriString = uriString,
            key = key,
            keysetPassword = keysetPassword,
            keysetAssociatedData = keysetAssociatedData
        )
    }

    override fun load(uriString: String, keysetPassword: String): KeyReader.Result {
        return keyReader(
            uriString = uriString,
            keysetPassword = keysetPassword,
            keysetAssociatedData = keysetAssociatedData
        )
    }

    override fun getFileAeadList(): List<String> {
        return KeysetTemplates.Stream.entries
            .filter { it.name.endsWith("MB") }
            .map { it.name.removeSuffix("_1MB").lowercase() }
    }

    override fun getTextAeadList(): List<String> {
        return KeysetTemplates.AEAD.entries.map { it.name.lowercase() }
    }
}