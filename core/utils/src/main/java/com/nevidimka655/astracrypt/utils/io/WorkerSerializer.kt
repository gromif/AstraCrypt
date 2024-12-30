package com.nevidimka655.astracrypt.utils.io

import java.io.File
import kotlin.collections.joinToString

class WorkerSerializer(
    private val filesUtil: FilesUtil
) {

    fun saveStringListToFile(arr: List<String>): File {
        val file = filesUtil.createTempFileInCache()
        val text = arr.joinToString(separator = "\n").trimEnd()
        file.writeText(text)
        return file
    }

}