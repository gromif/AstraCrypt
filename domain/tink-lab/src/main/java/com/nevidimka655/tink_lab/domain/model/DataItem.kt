package com.nevidimka655.tink_lab.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
    @SerialName("a")
    val title: String,

    @SerialName("b")
    val type: DataType
)