package com.nevidimka655.astracrypt.domain.model.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class StorageFlags {

    @Serializable
    @SerialName("img")
    class Image: StorageFlags() {

        @SerialName("c1")
        var resolution = ""

    }

    @Serializable
    @SerialName("vid")
    class Video: StorageFlags() {

        @SerialName("d1")
        var resolution = ""

    }

    @Serializable
    @SerialName("mus")
    class Music: StorageFlags() {
        @SerialName("b1") var title: String? = null
        @SerialName("b2") var sampleRate = 0
        @SerialName("b3") var author: String? = null
    }

    @Serializable
    @SerialName("app")
    class App: StorageFlags()
}