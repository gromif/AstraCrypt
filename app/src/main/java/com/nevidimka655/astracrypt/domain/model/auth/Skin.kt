package com.nevidimka655.astracrypt.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Skin {

    @Serializable
    @SerialName("a")
    class Calculator: Skin() {

        @SerialName("a")
        var combinationHash = ""

    }

}