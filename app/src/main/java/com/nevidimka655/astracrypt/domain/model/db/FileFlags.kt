package com.nevidimka655.astracrypt.domain.model.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class FileFlags {

    @Serializable
    @SerialName("img")
    class Image: FileFlags() {

        @SerialName("c1")
        var resolution = ""

    }

    @Serializable
    @SerialName("vid")
    class Video: FileFlags() {

        @SerialName("d1")
        var resolution = ""

    }

    @Serializable
    @SerialName("mus")
    class Music: FileFlags() {
        @SerialName("b1") var title: String? = null
        @SerialName("b2") var sampleRate = 0
        @SerialName("b3") var author: String? = null
    }

    @Serializable
    @SerialName("app")
    class App: FileFlags()
}