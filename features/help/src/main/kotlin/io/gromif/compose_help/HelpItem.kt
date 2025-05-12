package io.gromif.compose_help

import kotlinx.serialization.Serializable

@Serializable
data class HelpItem(
    val header: Int,
    val text: Int
)