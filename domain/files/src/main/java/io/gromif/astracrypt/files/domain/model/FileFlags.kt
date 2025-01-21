package io.gromif.astracrypt.files.domain.model

sealed class FileFlags {

    data class Image(
        val resolution: String
    ): FileFlags()

    data class Video(
        val resolution: String
    ): FileFlags()

    data class Audio(
        val title: String?,
        val sampleRate: Int,
        val author: String?
    ): FileFlags()

}