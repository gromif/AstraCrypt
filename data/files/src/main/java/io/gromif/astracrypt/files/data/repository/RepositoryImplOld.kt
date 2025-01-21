package io.gromif.astracrypt.files.data.repository

import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.tuples.DatabaseTransformTuple
import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.db.tuples.FilesDirMinimalTuple
import io.gromif.astracrypt.files.data.db.tuples.OpenTuple
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.domain.model.FileType
import kotlinx.coroutines.flow.Flow

class RepositoryImplOld(private val dao: FilesDao) {

    suspend fun getTypeById(id: Long): FileType {
        return FileType.entries[dao.getTypeById(id)]
    }

    suspend fun getFilesCountFlow(dirId: Long): Int {
        return dao.getFilesCountFlow(dirId)
    }

    suspend fun getListDataToExportFromDir(dirId: Long): List<ExportTuple> {
        return dao.getListDataToExport(dirId)
    }

    suspend fun getDataToExport(itemId: Long): ExportTuple {
        return dao.getDataToExport(itemId)
    }

    suspend fun getDataForOpening(id: Long): OpenTuple {
        return dao.getDataToOpen(id)
    }

    suspend fun getParentDirInfo(dirId: Long): FilesDirMinimalTuple? {
        return dao.getParentDirInfo(dirId)
    }

    suspend fun getAbsolutePath(
        parentId: Long,
        childName: String
    ): String {
        var path = "/$childName"
        suspend fun dirIterator(dirId: Long) {
            val dirObj = getParentDirInfo(dirId)
            if (dirObj != null) {
                path = "/${dirObj.name}$path"
                if (dirObj.parentDirectoryId != 0L) dirIterator(dirObj.parentDirectoryId)
            }
        }
        if (parentId != 0L) dirIterator(parentId)
        return path
    }

    suspend fun getDatabaseTransformItems(
        pageSize: Int,
        pageIndex: Int
    ): List<DatabaseTransformTuple> {
        return dao.getDatabaseTransformItems(pageSize, pageIndex)
    }

    /*suspend fun getFolderContent(id: Long): DetailsFolderContent {
        var foldersCount = 0
        var filesCount = 0
        suspend fun dirIterator(dirId: Long) {
            val dirsList = getDirIdsList(dirId)
            filesCount += getFilesCountFlow(dirId)
            foldersCount += dirsList.size
            dirsList.forEach {
                dirIterator(it)
            }
        }
        dirIterator(id)
        return DetailsFolderContent(foldersCount, filesCount)
    }*/

    fun getRecentFilesFlow(): Flow<List<PagerTuple>> {
        return dao.getRecentFilesFlow()
    }
}