package io.gromif.tinkLab.data.repository

import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.model.Repository
import io.gromif.tinkLab.domain.util.KeyGenerator
import io.gromif.tinkLab.domain.util.KeyReader
import io.gromif.tinkLab.domain.util.KeyWriter

private val keysetAssociatedData = "labKey".toByteArray()

class RepositoryImpl(
    private val keyGenerator: KeyGenerator,
    private val keyWriter: KeyWriter,
    private val keyReader: KeyReader,
) : Repository {

    override fun createKey(dataType: DataType, aeadType: String): Key {
        return keyGenerator(dataType = dataType, aeadType = aeadType)
    }

    override fun save(key: Key, path: String, password: String) {
        keyWriter(
            uriString = path,
            key = key,
            keysetPassword = password,
            keysetAssociatedData = keysetAssociatedData
        )
    }

    override fun load(path: String, password: String): KeyReader.Result {
        return keyReader(
            uriString = path,
            keysetPassword = password,
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
