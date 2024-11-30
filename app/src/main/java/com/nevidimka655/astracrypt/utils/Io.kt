package com.nevidimka655.astracrypt.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateFormat
import androidx.core.content.FileProvider
import com.nevidimka655.astracrypt.utils.extensions.recreate
import java.io.File
import java.text.DecimalFormat

@SuppressLint("StaticFieldLeak")
val IO = Io(context = Engine.appContext) // TODO: Remove top-level declaration

class Io (
    private val context: Context
) {
    private val filesDir get() = context.filesDir
    private val exportedCacheDir = File(cacheDir, "exp").also { it.mkdir() }
    val cacheDir: File get() = context.cacheDir

    val dataDir = File("$filesDir/data")

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