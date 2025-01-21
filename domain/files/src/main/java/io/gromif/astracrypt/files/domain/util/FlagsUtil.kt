package io.gromif.astracrypt.files.domain.util

import io.gromif.astracrypt.files.domain.model.FileType

interface FlagsUtil {

    suspend fun getFlags(type: FileType, path: String): String?

}