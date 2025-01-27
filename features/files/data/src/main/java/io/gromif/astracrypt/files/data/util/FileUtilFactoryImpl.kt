package io.gromif.astracrypt.files.data.util

import android.content.Context
import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.domain.util.FileUtil

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