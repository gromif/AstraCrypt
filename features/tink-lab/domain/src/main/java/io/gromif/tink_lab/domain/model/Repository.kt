package io.gromif.tink_lab.domain.model

import io.gromif.tink_lab.domain.util.KeyReader

interface Repository {

    fun createKey(dataType: DataType, aeadType: String) : Key

    fun save(key: Key, path: String, password: String)

    fun load(path: String, password: String): KeyReader.Result

    fun getFileAeadList() : List<String>

    fun getTextAeadList() : List<String>

}