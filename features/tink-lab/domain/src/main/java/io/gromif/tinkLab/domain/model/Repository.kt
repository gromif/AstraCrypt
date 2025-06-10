package io.gromif.tinkLab.domain.model

import io.gromif.tinkLab.domain.util.KeyReader

interface Repository {

    fun createKey(dataType: DataType, aeadType: String): Key

    fun save(key: Key, path: String, password: String)

    fun load(path: String, password: String): KeyReader.Result

    fun getFileAeadList(): List<String>

    fun getTextAeadList(): List<String>
}
