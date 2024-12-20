package com.nevidimka655.astracrypt.data.io

import android.content.Context
import android.text.format.DateFormat
import androidx.core.content.FileProvider
import java.io.File

class FilesService (
    private val context: Context,
    private val randomizer: Randomizer
) {
    private val filesDir get() = context.filesDir
    private val exportedCacheDir = File(cacheDir, "exp").also { it.mkdir() }
    val cacheDir: File get() = context.cacheDir

    val dataDir = File("$filesDir/data")

    fun createTempFileInCache(): File =
        File.createTempFile(randomizer.generateUrlSafeString(5), null, cacheDir)

    fun getExportedCacheCameraFile(): File {
        val pattern = "dd-MM-yyyy_HH:mm:ss"
        val date = DateFormat.format(pattern, System.currentTimeMillis()).toString()
        val name = "IMG_$date.jpg"
        return getExportedCacheFile(name = name)
    }

    fun getExportedCacheFile(name: String) = File(exportedCacheDir, name).also {
        it.createNewFile()
    }

    fun getExportedCacheFileUri(file: File) = FileProvider.getUriForFile(
        context,
        context.applicationInfo.packageName,
        file
    )

    fun clearExportedCache() = exportedCacheDir.listFiles()?.forEach {
        it.deleteRecursively()
    }

    fun getLocalFile(relativePath: String?) = File("$dataDir/$relativePath")

    fun getProfileIconFile() = File("$filesDir/icon")

}