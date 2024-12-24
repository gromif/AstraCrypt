package com.nevidimka655.tink_lab.domain.model

interface Repository {

    fun createKey(
        keysetPassword: String,
        dataType: DataType,
        aeadType: String
    ) : Key

    fun getFileAeadList() : List<String>

    fun getTextAeadList() : List<String>

}