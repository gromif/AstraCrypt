package com.nevidimka655.astracrypt.features.lab

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LabCombineZipManager {

    val mutableListOfFiles = mutableStateListOf<CombineZipFile>()
    val mutableListOfSources = mutableStateListOf<CombineZipFile>()

    suspend fun addFiles(uris: List<Uri>, context: Context) = withContext(Dispatchers.IO) {
        uris.forEach { uri ->
            val document = DocumentFile.fromSingleUri(context, uri)
            mutableListOfFiles.add(
                CombineZipFile(name = document?.name ?: "", uri = uri)
            )
        }
    }

    suspend fun setSourceFile(uri: Uri, context: Context) = withContext(Dispatchers.IO) {
        val document = DocumentFile.fromSingleUri(context, uri)
        mutableListOfSources.add(
            CombineZipFile(name = document?.name ?: "", uri = uri)
        )
    }

    suspend fun start(destinationUri: Uri) {
//        WorkerFactory.startCombinedZipWorker(
//            sourceUri = mutableListOfSources[0].uri,
//            destinationUri = destinationUri,
//            zipFilesContentStringArray = mutableListOfFiles.map { it.uri.toString() }
//                .toTypedArray()
//        )
        clearFiles()
        clearSources()
    }

    fun clearFiles() = mutableListOfFiles.clear()
    fun clearSources() = mutableListOfSources.clear()

}

data class CombineZipFile(
    val name: String = "",
    val uri: Uri = Uri.EMPTY
)