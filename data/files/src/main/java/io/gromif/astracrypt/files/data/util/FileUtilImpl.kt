package io.gromif.astracrypt.files.data.util

import android.content.Context
import android.net.Uri
import androidx.core.provider.DocumentsContractCompat
import androidx.documentfile.provider.DocumentFile
import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.util.FileUtil

class FileUtilImpl(
    private val context: Context,
    private val fileHandler: FileHandler,
    private val uriMapper: Mapper<String, Uri>
): FileUtil {
    private var uri: Uri = Uri.EMPTY
    private var file: DocumentFile? = null

    override fun open(path: String): Boolean {
        uri = uriMapper(path)
        file = DocumentFile.fromSingleUri(context, uri)
        return file != null
    }

    override suspend fun write(): String? {
        context.contentResolver.openInputStream(uri)?.use {
            return fileHandler.writeFile(input = it)
        }
        return null
    }

    override fun length(): Long? {
        return file?.length()?.takeIf { it != 0L }
    }

    override fun getName(): String? {
        return file?.name
    }

    override fun creationTime(): Long {
        return file?.lastModified() ?: System.currentTimeMillis()
    }

    override fun delete() {
        file?.delete()
        if (file?.exists() == true) file?.parentFile?.let { parent ->
            DocumentsContractCompat.removeDocument(context.contentResolver, uri, parent.uri)
        }
    }

    override fun parseType(): FileType {
        val mimeType = file?.type ?: context.contentResolver.getType(uri)
        return mimeType?.let {
            when {
                mimeType.startsWith("image") -> FileType.Photo
                mimeType.startsWith("audio") -> FileType.Music
                mimeType.startsWith("video") -> FileType.Video
                mimeType.startsWith("text") -> FileType.Text
                else -> FileType.Other
            }
        } ?: FileType.Other
    }

}