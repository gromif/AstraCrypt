package com.nevidimka655.tink_lab.domain.model

import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataItem(
    @SerialName("a")
    @StringRes
    val titleResId: Int,

    @SerialName("b")
    val type: DataType
)