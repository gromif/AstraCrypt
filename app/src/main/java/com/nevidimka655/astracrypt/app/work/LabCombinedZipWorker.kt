package com.nevidimka655.astracrypt.app.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.extensions.contentResolver
import com.nevidimka655.astracrypt.app.utils.Api
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.use
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@HiltWorker
class LabCombinedZipWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val workManager: WorkManager
) : CoroutineWorker(appContext, params) {

    object Args {
        const val SOURCE_URI = "a1"
        const val TARGET_URI = "a2"
        const val ZIP_FILE_URI = "a3"
    }

    private val notificationId = 203

    override suspend fun doWork() = withContext(defaultDispatcher) {
        var workerResult = Result.success()
        setForeground(getForegroundInfo())
        val destinationUri = inputData.getString(Args.TARGET_URI)!!.toUri()
        val sourceUri = inputData.getString(Args.SOURCE_URI)!!.toUri()
        val destinationDocument = DocumentFile.fromSingleUri(applicationContext, destinationUri)!!
        val zipFileContentUrisFile = File(inputData.getString(Args.ZIP_FILE_URI)!!)
        try {
            contentResolver.openOutputStream(destinationUri)?.use { out ->
                contentResolver.openInputStream(sourceUri)?.use { it.copyTo(out) }
                ZipOutputStream(out).use { zipOut ->
                    zipFileContentUrisFile.forEachLine { line ->
                        val documentUri = Uri.parse(line)
                        val currentDocument = DocumentFile
                            .fromSingleUri(applicationContext, documentUri)!!
                        val zipEntry = ZipEntry(currentDocument.name!!)
                        zipOut.putNextEntry(zipEntry)
                        contentResolver.openInputStream(documentUri)?.use { it.copyTo(zipOut) }
                        zipOut.closeEntry()
                    }
                }
            }
        } catch (e: Exception) {
            e.toString()
            destinationDocument.delete()
            workerResult = Result.failure()
        }
        zipFileContentUrisFile.delete()
        workerResult
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val channelId = applicationContext.getString(
            R.string.notification_channel_fileOperations_id
        )
        val title = applicationContext.getString(R.string.dialog_exporting)
        val cancelText = applicationContext.getString(android.R.string.cancel)
        // This PendingIntent can be used to cancel the worker
        val workerStopPendingIntent = workManager.createCancelPendingIntent(id)
        // Create a Notification channel if necessary
        if (Api.atLeast8()) createChannel()
        val notification = NotificationCompat.Builder(applicationContext, channelId).apply {
            setContentTitle(title)
            foregroundServiceBehavior = NotificationCompat.FOREGROUND_SERVICE_DEFAULT
            setTicker(title)
            setProgress(100, 0, true)
            setSmallIcon(R.drawable.ic_notification_app_icon)
            setOngoing(true)
            addAction(R.drawable.ic_close, cancelText, workerStopPendingIntent)
        }.build()
        return ForegroundInfo(notificationId, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = applicationContext.getString(R.string.notification_channel_fileOperations)
        val descriptionText = applicationContext.getString(
            R.string.notification_channel_fileOperations_desc
        )
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channelId = applicationContext.getString(
            R.string.notification_channel_fileOperations_id
        )
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)
    }

}