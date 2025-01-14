package io.gromif.astracrypt.files.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileSourceDto(
    @SerialName("a") val path: String,
    @SerialName("b") val aeadIndex: Int
)