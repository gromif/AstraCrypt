package io.gromif.tink_lab.presentation.work

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
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.google.crypto.tink.StreamingAead
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeystore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.utils.Api
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.core.extensions.streamingAead
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltWorker
internal class TinkLabFilesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val keysetParser: KeysetParser,
    private val workManager: WorkManager,
    private val base64Encoder: Base64Encoder,
    private val stringToUriMapper: Mapper<String, Uri>
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
        const val ANDROID_KEYSET_ALIAS = "TINK_LAB_FILES_WORKER_DATA_KEY"
        const val ASSOCIATED_DATA = "workerAD_labFiles"
    }

    private val notificationId = 202

    override suspend fun doWork() = withContext(defaultDispatcher) {
        var workerResult = Result.success()
        setForeground(getForegroundInfo())
        TinkConfig.register()

        val dataAead = AndroidKeystore.getAead(ANDROID_KEYSET_ALIAS)
        val dataAD = ASSOCIATED_DATA.toByteArray()
        val sourceUriArray = inputData.getStringArray(Args.SOURCE_URI_ARRAY)!!.map {
            val decodedBase64 = base64Encoder.decode(it)
            val decryptedUri = dataAead.decrypt(decodedBase64, dataAD).decodeToString()
            stringToUriMapper(decryptedUri)
        }
        val targetUri = stringToUriMapper(
            dataAead.decrypt(
                base64Encoder.decode(
                    inputData.getString(Args.TARGET_URI)!!.toByteArray()
                ), dataAD
            ).decodeToString()
        )
        val associatedData = dataAead.decrypt(
            base64Encoder.decode(
                inputData.getString(Args.ENCRYPTED_AD)!!.toByteArray()
            ), dataAD
        )
        val keysetHandle = keysetParser(
            dataAead.decrypt(
                base64Encoder.decode(
                    inputData.getString(Args.ENCRYPTED_KEYSET)!!.toByteArray()
                ), dataAD
            ).decodeToString()
        )
        AndroidKeystore.deleteKey(ANDROID_KEYSET_ALIAS)

        val mode = inputData.getBoolean(Args.MODE, false)
        val destinationRoot = DocumentFile.fromTreeUri(applicationContext, targetUri)!!
        val datePattern = "dd_mm_yyyy_hh:mm:ss"
        val date = DateFormat.format(datePattern, System.currentTimeMillis()).toString()
        val destination = destinationRoot.createDirectory("Exported_$date")!!
        val streamAead = keysetHandle.streamingAead()
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
        } catch (_: Exception) {
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

}