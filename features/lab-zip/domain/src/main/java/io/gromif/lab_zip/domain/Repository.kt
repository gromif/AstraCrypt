package io.gromif.lab_zip.domain

interface Repository {

    fun getSourceFileInfo(path: String): FileInfo

    fun getFilesInfo(paths: List<String>): List<FileInfo>

}