package com.nevidimka655.astracrypt.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class StorageItemFlags {

    @Serializable
    @SerialName("img")
    class Image: StorageItemFlags() {

        @SerialName("c1")
        var resolution = ""

    }

    @Serializable
    @SerialName("vid")
    class Video: StorageItemFlags() {

        @SerialName("d1")
        var resolution = ""

    }

    @Serializable
    @SerialName("mus")
    class Music: StorageItemFlags() {
        @SerialName("b1") var title: String? = null
        @SerialName("b2") var sampleRate = 0
        @SerialName("b3") var author: String? = null
    }

    @Serializable
    @SerialName("app")
    class App: StorageItemFlags()
}