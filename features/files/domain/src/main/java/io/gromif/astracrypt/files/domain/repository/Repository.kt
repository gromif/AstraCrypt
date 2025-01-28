package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ExportData
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.model.FileType
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun get(
        aeadInfo: AeadInfo? = null,
        id: Long,
    ): FileItem

    suspend fun getFolderIds(
        parentId: Long,
        recursively: Boolean = true
    ): List<Long>

    suspend fun insert(
        aeadInfo: AeadInfo? = null,
        parent: Long,
        name: String,
        fileState: FileState = FileState.Default,
        fileType: FileType,
        file: String? = null,
        fileAead: Int = -1,
        preview: String? = null,
        previewAead: Int = -1,
        flags: String? = null,
        creationTime: Long,
        size: Long,
    )

    suspend fun createFolder(name: String, parentId: Long)

    suspend fun delete(ids: List<Long>)

    suspend fun move(ids: List<Long>, parentId: Long)

    suspend fun rename(id: Long, newName: String)

    suspend fun setStarred(ids: List<Long>, state: Boolean)

    suspend fun export(ids: List<Long>, outputPath: String)

    suspend fun exportPrivately(id: Long): String?

    fun getRecentFilesList(): Flow<List<FileItem>>

    suspend fun getFilesCountFlow(dirId: Long): Int

    suspend fun getListDataToExportFromDir(dirId: Long): List<ExportData>

    suspend fun getDataToExport(itemId: Long): ExportData

}