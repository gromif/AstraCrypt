package io.gromif.astracrypt.files.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class FileFlagsDto {

    @SerialName("img")
    @Serializable
    data class Image(

        @SerialName("a")
        val resolution: String = ""

    ): FileFlagsDto()

    @SerialName("vid")
    @Serializable
    data class Video(

        @SerialName("a")
        val resolution: String = ""

    ): FileFlagsDto()

    @SerialName("aud")
    @Serializable
    data class Audio(
        @SerialName("a")
        val title: String? = null,

        @SerialName("b")
        val sampleRate: Int = 0,

        @SerialName("c")
        val author: String? = null
    ): FileFlagsDto()

}