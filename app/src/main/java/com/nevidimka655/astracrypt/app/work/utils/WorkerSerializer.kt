package com.nevidimka655.astracrypt.app.work.utils

import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.utils.Io
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class WorkerSerializer @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val io: Io
) {

    fun saveJsonToFile(jsonStr: String): String {
        val file = io.createTempFileInCache()
        file.writeText(jsonStr)
        return file.toString()
    }

    suspend fun saveStringArrayToFile(arr: Array<String>) = withContext(defaultDispatcher) {
        val file = io.createTempFileInCache()
        val text = arr.joinToString(separator = "\n").trimEnd()
        file.writeText(text)
        file.toString()
    }

    fun readJsonFromFile(filePath: String): String? {
        val file = File(filePath)
        return if (file.exists()) file.readText() else null
    }

}