package com.nevidimka655.astracrypt.domain.model.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StorageFileTime(
    @SerialName("a") val creation: Long = 0,
    @SerialName("b") val modification: Long = 0
)