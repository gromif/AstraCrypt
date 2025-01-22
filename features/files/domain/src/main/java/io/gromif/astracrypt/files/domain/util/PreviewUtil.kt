package io.gromif.astracrypt.files.domain.util

import io.gromif.astracrypt.files.domain.model.FileType

interface PreviewUtil {

    suspend fun getPreviewPath(
        type: FileType,
        path: String
    ): String?

}