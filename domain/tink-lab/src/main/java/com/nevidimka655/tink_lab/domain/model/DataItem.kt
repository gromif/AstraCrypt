package com.nevidimka655.tink_lab.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
    @SerialName("a")
    val titleResId: Int,

    @SerialName("b")
    val type: DataType
)