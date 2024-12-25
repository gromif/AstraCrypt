package com.nevidimka655.tink_lab.domain.model

import com.nevidimka655.tink_lab.domain.util.KeyReader

interface Repository {

    fun createKey(dataType: DataType, aeadType: String) : Key

    fun save(key: Key, uriString: String, keysetPassword: String)

    fun load(uriString: String, keysetPassword: String): KeyReader.Result

    fun getFileAeadList() : List<String>

    fun getTextAeadList() : List<String>

}