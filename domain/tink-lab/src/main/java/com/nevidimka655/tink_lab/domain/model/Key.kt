package com.nevidimka655.tink_lab.domain.model

data class Key(
    val dataType: DataType = DataType.Files,
    val aeadType: String = "",
    val encryptedKeyset: String? = null,
    val hash: String = ""
)