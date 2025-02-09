package io.gromif.astracrypt.files.data.util

import android.content.Context
import android.net.Uri
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.utils.Mapper

class FileUtilFactoryImpl(
    private val context: Context,
    private val fileHandler: FileHandler,
    private val uriMapper: Mapper<String, Uri>
): FileUtil.Factory {
    override suspend fun create(): FileUtil {
        return FileUtilImpl(
            context = context,
            fileHandler = fileHandler,
            uriMapper = uriMapper
        )
    }
}