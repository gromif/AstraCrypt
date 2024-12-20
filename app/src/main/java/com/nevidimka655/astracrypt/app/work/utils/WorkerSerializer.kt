package com.nevidimka655.astracrypt.app.work.utils

import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.data.io.FilesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class WorkerSerializer @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val filesService: FilesService
) {

    fun saveJsonToFile(jsonStr: String): String {
        val file = filesService.createTempFileInCache()
        file.writeText(jsonStr)
        return file.toString()
    }

    suspend fun saveStringArrayToFile(arr: Array<String>) = withContext(defaultDispatcher) {
        val file = filesService.createTempFileInCache()
        val text = arr.joinToString(separator = "\n").trimEnd()
        file.writeText(text)
        file.toString()
    }

    fun readJsonFromFile(filePath: String): String? {
        val file = File(filePath)
        return if (file.exists()) file.readText() else null
    }

}