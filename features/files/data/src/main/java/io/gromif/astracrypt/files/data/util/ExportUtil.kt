package io.gromif.astracrypt.files.data.util

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.domain.model.ItemType
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive

class ExportUtil(
    private val context: Context,
    private val filesDao: FilesDao,
    private val fileHandler: FileHandler,
    private val filesUtil: FilesUtil,
    private val stringToUriMapper: Mapper<Uri, String>
) : AutoCloseable {
    private var outputDocumentFile: DocumentFile? = null

    fun createDocumentFile(uri: Uri): Boolean {
        outputDocumentFile = DocumentFile.fromTreeUri(context, uri)
        return outputDocumentFile != null
    }

    suspend fun startExternally(idList: List<Long>) = coroutineScope {
        val outputFolder = outputDocumentFile!!
        val folderMap = mutableMapOf<Long, DocumentFile>()
        idList.forEach { folderMap[it] = outputFolder }
        val deque = ArrayDeque<Long>()
        deque.addAll(idList)
        while (isActive && deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val currentFolder = folderMap.remove(currentId) ?: continue
            val exportData = filesDao.getExportData(id = currentId)
            if (exportData.type == ItemType.Folder) {
                val newFolder = currentFolder.createDirectory(exportData.name) ?: continue
                val idList = filesDao.getIdList(parent = currentId)
                idList.forEach { folderMap[it] = newFolder }
                deque.addAll(idList)
            } else {
                val outputFile = currentFolder.createFile("text/binary", exportData.name) ?: continue
                fileHandler.exportFile(
                    outputStream = context.contentResolver.openOutputStream(outputFile.uri)!!,
                    relativePath = exportData.file!!,
                    aeadIndex = exportData.fileAead
                )
                if (!isActive) outputFile.delete()
            }
        }
    }

    suspend fun startPrivately(id: Long): String? = coroutineScope {
        val exportData = filesDao.getExportData(id)
        val exportFile = filesUtil.getExportedCacheFile(exportData.name)
        val outputUri = filesUtil.getExportedCacheFileUri(file = exportFile)
        fileHandler.exportFile(
            outputStream = context.contentResolver.openOutputStream(outputUri)!!,
            relativePath = exportData.file!!,
            aeadIndex = exportData.fileAead
        )
        if (!isActive) exportFile.delete() else return@coroutineScope stringToUriMapper(outputUri)
        null
    }

    override fun close() {
        outputDocumentFile = null
    }

}