package io.gromif.astracrypt.profile.domain.model

data class ValidationRulesDto(
    val minNameLength: Int,
    val maxNameLength: Int,

    val iconSize: Int,
    val iconQuality: Int
)