package com.nevidimka655.astracrypt.view.ui.settings.security.authentication

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Camouflage {

    @Serializable
    @SerialName("none")
    object None : Camouflage()

    @Serializable
    @SerialName("calc")
    class Calculator: Camouflage() {

        @SerialName("a1")
        var numberCombination = ""

    }

}