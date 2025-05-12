package io.gromif.tink_lab.domain.model

data class Key(
    val dataType: DataType = DataType.Files,
    val aeadType: String = "",
    val rawKeyset: String = ""
)