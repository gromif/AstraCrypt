package io.gromif.astracrypt.files.domain.util

import io.gromif.astracrypt.files.domain.model.ItemType

interface PreviewUtil {

    suspend fun getPreviewPath(
        type: ItemType,
        path: String
    ): String?
}
