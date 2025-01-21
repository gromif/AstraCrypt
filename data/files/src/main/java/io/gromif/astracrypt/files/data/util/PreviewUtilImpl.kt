package io.gromif.astracrypt.files.data.util

import io.gromif.astracrypt.files.data.factory.preview.AudioPreviewFactory
import io.gromif.astracrypt.files.data.factory.preview.DefaultPreviewFactory
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.util.PreviewUtil

class PreviewUtilImpl(
    private val fileHandler: FileHandler,
    private val defaultPreviewFactory: DefaultPreviewFactory,
    private val audioPreviewFactory: AudioPreviewFactory
): PreviewUtil {

    override suspend fun getPreviewPath(
        type: FileType,
        path: String,
    ): String? {
        val bytes = when(type) {
            FileType.Music -> audioPreviewFactory.create(path)
            else -> defaultPreviewFactory.create(path)
        }
        return bytes?.let {
            fileHandler.writePreview(it)?.toString()
        }
    }

}