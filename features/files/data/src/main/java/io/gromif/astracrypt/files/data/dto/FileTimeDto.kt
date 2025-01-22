package io.gromif.astracrypt.files.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileTimeDto(
    @SerialName("a") val creation: Long = 0,
    @SerialName("b") val modification: Long = 0
)