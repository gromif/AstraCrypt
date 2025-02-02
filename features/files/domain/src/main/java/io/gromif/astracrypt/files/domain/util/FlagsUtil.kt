package io.gromif.astracrypt.files.domain.util

import io.gromif.astracrypt.files.domain.model.ItemType

interface FlagsUtil {

    suspend fun getFlags(type: ItemType, path: String): String?

}