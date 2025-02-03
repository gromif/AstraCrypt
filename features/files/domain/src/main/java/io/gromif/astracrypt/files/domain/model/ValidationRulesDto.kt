package io.gromif.astracrypt.files.domain.model

data class ValidationRulesDto(
    val minNameLength: Int,
    val maxNameLength: Int,
    val maxBackstackNameLength: Int,
)