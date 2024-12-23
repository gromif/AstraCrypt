package com.nevidimka655.tink_lab.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DataType {
    @SerialName("a") Files,
    @SerialName("b") Text
}