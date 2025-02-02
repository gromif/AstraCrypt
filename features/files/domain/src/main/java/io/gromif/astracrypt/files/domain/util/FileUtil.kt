package io.gromif.astracrypt.files.domain.util

import io.gromif.astracrypt.files.domain.model.ItemType

interface FileUtil {

    interface Factory {
        suspend fun create(): FileUtil
    }

    fun open(path: String): Boolean

    suspend fun write(): String?

    fun length(): Long?

    fun getName(): String?

    fun creationTime(): Long

    fun delete()

    fun parseType(): ItemType

}