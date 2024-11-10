package com.nevidimka655.astracrypt.work.utils

import com.nevidimka655.astracrypt.utils.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object WorkerSerializer {

    fun saveJsonToFile(jsonStr: String): String {
        val file = IO.createTempFileInCache()
        file.writeText(jsonStr)
        return file.toString()
    }

    suspend fun saveStringArrayToFile(arr: Array<String>) = withContext(Dispatchers.IO) {
        val file = IO.createTempFileInCache()
        val text = arr.joinToString(separator = "\n").trimEnd()
        file.writeText(text)
        file.toString()
    }

    fun readJsonFromFile(filePath: String): String? {
        val file = File(filePath)
        return if (file.exists()) file.readText() else null
    }

}