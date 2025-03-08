package io.gromif.lab_zip.presentation.work

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
import com.google.crypto.tink.integration.android.AndroidKeystore
import com.nevidimka655.astracrypt.resources.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.gromif.astracrypt.utils.Api
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@HiltWorker
internal class CombinedZipWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val workManager: WorkManager,
    private val base64Encoder: Base64Encoder,
    private val stringToUriMapper: Mapper<String, Uri>,
) : CoroutineWorker(context, params) {
    private val notificationId = 203

    override suspend fun doWork() = withContext(defaultDispatcher) {
        var workerResult = Result.success()
        setForeground(getForegroundInfo())

        val dataAead = AndroidKeystore.getAead(ANDROID_KEYSET_ALIAS)
        val dataAD = ASSOCIATED_DATA.toByteArray()
        val destinationUriString = dataAead.decrypt(
            base64Encoder.decode(inputData.getString(Args.TARGET_URI)!!), dataAD
        ).decodeToString()
        val sourceUriString = dataAead.decrypt(
            base64Encoder.decode(inputData.getString(Args.SOURCE_URI)!!), dataAD
        ).decodeToString()
        val contentUrisFile = dataAead.decrypt(
            base64Encoder.decode(inputData.getString(Args.ZIP_FILE_URI)!!), dataAD
        ).decodeToString()
        AndroidKeystore.deleteKey(ANDROID_KEYSET_ALIAS)

        val sourceUri = stringToUriMapper(sourceUriString)
        val destinationUri = stringToUriMapper(destinationUriString)
        val destinationDocument = DocumentFile.fromSingleUri(applicationContext, destinationUri)!!
        val zipFileContentUrisFile = File(contentUrisFile)

        try {
            applicationContext.contentResolver.openOutputStream(destinationUri)?.use { out ->
                applicationContext.contentResolver.openInputStream(sourceUri)
                    ?.use { it.copyTo(out) }
                ZipOutputStream(out).use { zipOut ->
                    zipFileContentUrisFile.forEachLine { line ->
                        val documentUri = line.toUri()
                        val currentDocument = DocumentFile
                            .fromSingleUri(applicationContext, documentUri)!!
                        val zipEntry = ZipEntry(currentDocument.name!!)
                        zipOut.putNextEntry(zipEntry)
                        applicationContext.contentResolver.openInputStream(documentUri)
                            ?.use { it.copyTo(zipOut) }
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
        return if (Api.atLeast10()) ForegroundInfo(
            notificationId,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        ) else ForegroundInfo(notificationId, notification)
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

    object Args {
        const val SOURCE_URI = "a1"
        const val TARGET_URI = "a2"
        const val ZIP_FILE_URI = "a3"
    }

    companion object {
        const val ANDROID_KEYSET_ALIAS = "source"
        const val ASSOCIATED_DATA = "ForegroundInfo"
    }

}