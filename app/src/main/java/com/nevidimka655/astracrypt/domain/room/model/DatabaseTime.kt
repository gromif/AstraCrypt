package com.nevidimka655.astracrypt.domain.room.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseTime(
    @SerialName("a") val creation: Long = 0,
    @SerialName("b") val modification: Long = 0
)