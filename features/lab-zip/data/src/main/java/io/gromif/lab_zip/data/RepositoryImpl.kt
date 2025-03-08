package io.gromif.lab_zip.data

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import io.gromif.astracrypt.utils.Mapper
import io.gromif.lab_zip.domain.FileInfo
import io.gromif.lab_zip.domain.Repository

class RepositoryImpl(
    private val context: Context,
    private val stringToUriMapper: Mapper<String, Uri>
): Repository {
    override fun getSourceFileInfo(path: String): FileInfo {
        val uri = stringToUriMapper(path)
        val document = DocumentFile.fromSingleUri(context, uri)
        return FileInfo(
            name = document?.name ?: "",
            path = path
        )
    }

    override fun getFilesInfo(paths: List<String>): List<FileInfo> {
        return paths.map { getSourceFileInfo(path = it) }
    }
}