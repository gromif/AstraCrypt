package io.gromif.astracrypt.files.data.repository

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.ItemExporter
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.io.FilesUtil
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive

class DefaultItemExporter(
    private val context: Context,
    private val filesDao: FilesDao,
    private val fileHandler: FileHandler,
    private val filesUtil: FilesUtil,
    private val uriToString: Mapper<Uri, String>,
    private val stringToUri: Mapper<String, Uri>
) : ItemExporter {

    override suspend fun externalExport(
        idList: List<Long>,
        outputPath: String
    ) = coroutineScope {
        val outputFolder = DocumentFile.fromTreeUri(
            /* context = */
            context,
            /* treeUri = */
            stringToUri(outputPath)
        )!!

        val folderMap = mutableMapOf<Long, DocumentFile>()
        idList.forEach { folderMap[it] = outputFolder }

        val deque = ArrayDeque<Long>().apply { addAll(idList) }
        while (isActive && deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val currentFolder = folderMap.remove(currentId) ?: continue
            val exportData = filesDao.getExportData(id = currentId)

            if (exportData.type == ItemType.Folder) {
                currentFolder.createDirectory(exportData.name)?.let { newFolder ->
                    val idList = filesDao.getIdList(parent = currentId)
                    idList.forEach { folderMap[it] = newFolder }
                    deque.addAll(idList)
                }
            } else {
                currentFolder.createFile("text/binary", exportData.name)?.let { newFile ->
                    val outStream = context.contentResolver.openOutputStream(newFile.uri)!!
                    fileHandler.exportFile(
                        outputStream = outStream,
                        relativePath = exportData.file!!,
                        aeadIndex = exportData.fileAead
                    )
                    if (!isActive) newFile.delete()
                }
            }
        }
    }

    override suspend fun internalExport(id: Long): String {
        val exportData = filesDao.getExportData(id)
        val exportFile = filesUtil.getExportedCacheFile(exportData.name)
        val outputUri = filesUtil.getExportedCacheFileUri(file = exportFile)
        fileHandler.exportFile(
            outputStream = context.contentResolver.openOutputStream(outputUri)!!,
            relativePath = exportData.file!!,
            aeadIndex = exportData.fileAead
        )
        return uriToString(outputUri)
    }
}
