package com.nevidimka655.data.lab_zip.repository

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.nevidimka655.domain.lab_zip.FileInfo
import com.nevidimka655.domain.lab_zip.Repository
import io.gromif.astracrypt.utils.Mapper

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