package io.gromif.astracrypt.files.data.repository

import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.tuples.DatabaseTransformTuple
import io.gromif.astracrypt.files.data.db.tuples.FilesDirMinimalTuple

class RepositoryImplOld(private val dao: FilesDao) {

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
}