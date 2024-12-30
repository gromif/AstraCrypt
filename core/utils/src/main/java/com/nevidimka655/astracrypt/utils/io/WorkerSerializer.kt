package com.nevidimka655.astracrypt.utils.io

class WorkerSerializer(
    private val filesUtil: FilesUtil
) {

    fun saveStringArrayToFile(arr: Array<String>) {
        val file = filesUtil.createTempFileInCache()
        val text = arr.joinToString(separator = "\n").trimEnd()
        file.writeText(text)
        file.toString()
    }

}