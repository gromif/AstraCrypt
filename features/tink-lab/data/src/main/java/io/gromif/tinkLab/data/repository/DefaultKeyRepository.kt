package io.gromif.tinkLab.data.repository

import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.model.result.ReadKeyResult
import io.gromif.tinkLab.domain.repository.KeyRepository
import io.gromif.tinkLab.domain.util.KeyGenerator
import io.gromif.tinkLab.domain.util.KeyReader
import io.gromif.tinkLab.domain.util.KeyWriter

private val keysetAssociatedData = "labKey".toByteArray()

class DefaultKeyRepository(
    private val keyGenerator: KeyGenerator,
    private val keyWriter: KeyWriter,
    private val keyReader: KeyReader,
) : KeyRepository {

    override suspend fun createKey(dataType: DataType, aeadType: String): Key {
        return keyGenerator(dataType = dataType, aeadType = aeadType)
    }

    override suspend fun save(key: Key, path: String, password: String) {
        keyWriter(
            uriString = path,
            key = key,
            keysetPassword = password,
            keysetAssociatedData = keysetAssociatedData
        )
    }

    override suspend fun load(path: String, password: String): ReadKeyResult {
        return keyReader(
            uriString = path,
            keysetPassword = password,
            keysetAssociatedData = keysetAssociatedData
        )
    }
}
