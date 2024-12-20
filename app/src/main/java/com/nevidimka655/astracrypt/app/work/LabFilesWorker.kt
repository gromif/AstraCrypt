package com.nevidimka655.astracrypt.app.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.ServiceInfo
import android.net.Uri
import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.crypto.tink.JsonKeysetReader
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.StreamingAead
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.utils.Api
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.data.TinkConfig
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LabFilesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val keysetManager: KeysetManager,
    private val workManager: WorkManager
) : CoroutineWorker(context, params) {
    private val contentResolver: ContentResolver = applicationContext.contentResolver

    object Args {
        const val SOURCE_URI_ARRAY = "a1"
        const val TARGET_URI = "a2"
        const val ENCRYPTED_AD = "a3"
        const val ENCRYPTED_KEYSET = "a4"
        const val MODE = "a5"
    }

    companion object {
        const val keysetTransportAssociatedData = "b1"
        const val associatedDataTransportAssociatedData = "b2"
    }

    private val notificationId = 202

    override suspend fun doWork() = withContext(defaultDispatcher) {
        var workerResult = Result.success()
        setForeground(getForegroundInfo())
        TinkConfig.initStream()
        val aeadForKeyset = keysetManager.aead(KeysetTemplates.AEAD.AES256_GCM).aeadPrimitive()
        val keysetHandle = inputData.getString(Args.ENCRYPTED_KEYSET)!!.run {
            KeysetHandle.readWithAssociatedData(
                JsonKeysetReader.withBytes(fromBase64()),
                aeadForKeyset,
                keysetTransportAssociatedData.toByteArray()
            )
        }
        val associatedData = inputData.getString(Args.ENCRYPTED_AD)!!.run {
            aeadForKeyset.decrypt(
                fromBase64(),
                associatedDataTransportAssociatedData.toByteArray()
            )
        }
        val mode = inputData.getBoolean(Args.MODE, false)
        val destinationUri = inputData.getString(Args.TARGET_URI)!!.toUri()
        val sourceUriArray = inputData.getStringArray(Args.SOURCE_URI_ARRAY)!!.map { it.toUri() }
        val destinationRoot = DocumentFile.fromTreeUri(applicationContext, destinationUri)!!
        val datePattern = "dd_mm_yyyy_hh:mm:ss"
        val date = DateFormat.format(datePattern, System.currentTimeMillis()).toString()
        val destination = destinationRoot.createDirectory("Exported_$date")!!
        val streamAead = keysetHandle.streamingAeadPrimitive()
        try {
            sourceUriArray.forEach {
                iterator(
                    mode = mode,
                    stream = streamAead,
                    associatedData = associatedData,
                    destination = destination,
                    sourceUri = it
                )
            }
        } catch (e: Exception) {
            destination.delete()
            workerResult = Result.failure()
        }
        workerResult
    }

    private fun iterator(
        mode: Boolean,
        stream: StreamingAead,
        associatedData: ByteArray,
        destination: DocumentFile,
        sourceUri: Uri
    ) {
        val source = DocumentFile.fromSingleUri(applicationContext, sourceUri)!!
        val outputUri = destination.createFile(source.type!!, source.name!!)?.uri
        val input = contentResolver.openInputStream(sourceUri)!!
        val out = contentResolver.openOutputStream(outputUri!!, "wt")!!
        if (mode) stream.newEncryptingStream(out, associatedData).use { outputStream ->
            input.use { it.copyTo(outputStream) }
        } else stream.newDecryptingStream(input, associatedData).use { inputStream ->
            out.use { inputStream.copyTo(it) }
        }
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