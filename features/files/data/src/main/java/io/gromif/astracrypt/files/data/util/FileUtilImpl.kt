package io.gromif.astracrypt.files.data.util

import android.content.Context
import android.net.Uri
import androidx.core.provider.DocumentsContractCompat
import androidx.documentfile.provider.DocumentFile
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.utils.Mapper

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

    override fun parseType(): ItemType {
        val mimeType = file?.type ?: context.contentResolver.getType(uri)
        return mimeType?.let {
            when {
                mimeType.startsWith("image") -> ItemType.Photo
                mimeType.startsWith("audio") -> ItemType.Music
                mimeType.startsWith("video") -> ItemType.Video
                mimeType.startsWith("text") -> ItemType.Text
                else -> ItemType.Other
            }
        } ?: ItemType.Other
    }

}