package com.nevidimka655.astracrypt.utils

import android.text.format.DateFormat
import com.nevidimka655.astracrypt.utils.extensions.recreate
import java.io.File
import java.text.DecimalFormat

object IO {

    private val filesDir get() = Engine.appContext.filesDir
    val cacheDir: File get() = Engine.appContext.cacheDir

    private val exportedCacheDir by lazy { File(cacheDir, "exp").also { it.mkdir() } }

    val dataDir by lazy { File("$filesDir/data") }

    fun createTempFileInCache(): File =
        File.createTempFile(Randomizer.getUrlSafeString(5), null, cacheDir)

    fun getExportedCacheCameraFile(): File {
        val pattern = "dd-MM-yyyy_HH:mm:ss"
        val date = DateFormat.format(pattern, System.currentTimeMillis()).toString()
        val name = "IMG_$date.jpg"
        return getExportedCacheFile(name = name)
    }

    fun getExportedCacheFile(name: String) = File(exportedCacheDir, name).also {
        it.recreate()
    }

    fun clearExportedCache() = exportedCacheDir.listFiles()?.forEach {
        it.deleteRecursively()
    }

    fun getLocalFile(relativePath: String?) = File("$dataDir/$relativePath")

    fun getProfileIconFile() = File("$filesDir/icon")

    fun removeExtension(name: String): String {
        val lastPointIndex = name.lastIndexOf('.')
        return if (lastPointIndex != -1) name.substring(0, lastPointIndex)
        else name
    }

    fun bytesToHumanReadable(bytesSize: Long): String {
        val kilobytes = bytesSize / 1024.0
        val megabytes = kilobytes / 1024.0
        val gigabytes = megabytes / 1024.0
        val terabytes = gigabytes / 1024.0
        val decimalFormat = DecimalFormat("0.00")
        return when {
            terabytes > 1 -> decimalFormat.format(terabytes) + " Tb"
            gigabytes > 1 -> decimalFormat.format(gigabytes) + " Gb"
            megabytes > 1 -> decimalFormat.format(megabytes) + " Mb"
            kilobytes > 1 -> decimalFormat.format(kilobytes) + " Kb"
            else -> decimalFormat.format(bytesSize) + " B"
        }
    }

}