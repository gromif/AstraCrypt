package io.gromif.tinkLab.domain.model

data class Key(
    val dataType: DataType = DataType.Files,
    val aeadType: String = "",
    val rawKeyset: String = ""
)