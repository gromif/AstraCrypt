package com.nevidimka655.astracrypt.utils.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DatabaseColumns {
    @SerialName("a") ID,
    @SerialName("b") Name,
    @SerialName("c") ItemType,
    @SerialName("d") Parent,
    @SerialName("e") Size,
    @SerialName("f") State,
    @SerialName("g") Preview,
    @SerialName("h") File,
    @SerialName("i") Flags,
    @SerialName("j") Time
}