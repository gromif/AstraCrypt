package io.gromif.tinkLab.domain.repository

import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.util.KeyReader

interface KeyRepository {

    suspend fun createKey(dataType: DataType, aeadType: String): Key

    suspend fun save(
        key: Key,
        path: String,
        password: String
    )

    suspend fun load(path: String, password: String): KeyReader.Result
}
