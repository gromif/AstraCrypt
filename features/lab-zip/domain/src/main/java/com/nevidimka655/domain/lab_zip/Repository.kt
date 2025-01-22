package com.nevidimka655.domain.lab_zip

interface Repository {

    fun getSourceFileInfo(path: String): FileInfo

    fun getFilesInfo(paths: List<String>): List<FileInfo>

}