package com.nevidimka655.astracrypt.app.services

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
import com.nevidimka655.astracrypt.app.utils.Api
import com.nevidimka655.astracrypt.data.database.ExportTuple
import com.nevidimka655.astracrypt.data.io.FilesService
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.domain.repository.Repository
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.data.TinkConfig
import com.nevidimka655.crypto.tink.extensions.fromBase64
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@HiltWorker
class ExportFilesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val repository: Repository,
    private val keysetManager: KeysetManager,
    private val filesService: FilesService,
    private val workManager: WorkManager,
) : CoroutineWorker(context, params) {

    object Args {
        const val uriDirOutput = "yellow"
        const val itemId = "green"
        const val aeadInfo = "blue"
        const val associatedData = "red"
        const val TAG_ASSOCIATED_DATA_TRANSPORT = "orange"
    }

    private val notificationId = 201

    private val aeadInfo: AeadInfo by lazy {
        Json.decodeFromString(
            inputData.getString(Args.aeadInfo)!!
        )
    }

    override suspend fun doWork() = withContext(defaultDispatcher) {
        setForeground(getForegroundInfo())
        TinkConfig.initStream()
        shouldDecodeAssociatedData()
        val outputDirUri = inputData.getString(Args.uriDirOutput)!!
        val itemId = inputData.getLong(Args.itemId, 0)
        val startDir = createStartDocumentFile(outputDirUri.toUri())
        val itemIsFile = repository.getTypeById(id = itemId).isFile
        if (startDir != null) {
            if (itemIsFile) fileIterator(
                exportTuple = repository.getDataToExport(itemId),
                parentDir = startDir
            ) else directoryIterator(
                dirId = itemId,
                parentDir = startDir
            )
        }
        Result.success()
    }

    private fun createStartDocumentFile(startUri: Uri) =
        DocumentFile.fromTreeUri(applicationContext, startUri)

    private suspend fun directoryIterator(dirId: Long, parentDir: DocumentFile?) {
        val dirName = repository.getDataToExport(dirId).name
        val newDirectory = parentDir?.createDirectory(dirName)
        if (newDirectory != null) {
            repository.getListDataToExportFromDir(dirId).forEach {
                if (!isStopped) {
                    if (it.path.isNotEmpty()) fileIterator(it, newDirectory)
                    else directoryIterator(it.id, newDirectory)
                }
            }
        }
    }

    private fun fileIterator(exportTuple: ExportTuple, parentDir: DocumentFile) {
        /*val (_, name, encryptionType, path) = exportTuple
        val file = parentDir.createFile("text/binary", name)
        if (file != null) {
            val fileEncodedInputStream = io.getLocalFile(path).inputStream()
            val fileInputStream = if (encryptionType != -1) {
                keysetFactory.stream(KeysetTemplates.Stream.entries[encryptionType])
                    .streamingAeadPrimitive()
                    .newDecryptingStream(fileEncodedInputStream, keysetFactory.associatedData)
            } else fileEncodedInputStream
            contentResolver.openOutputStream(file.uri)?.use { output ->
                fileInputStream.use { input ->
                    val buffer = ByteArray(8192)
                    var bytes = input.read(buffer)
                    while (bytes >= 0 && !isStopped) {
                        output.write(buffer, 0, bytes)
                        bytes = input.read(buffer)
                    }
                }
            }
            if (isStopped) file.delete()
        }*/
    }

    private suspend fun shouldDecodeAssociatedData() {
        if (aeadInfo.bindAssociatedData) {
            val bytes = inputData.getString(Args.associatedData)!!.fromBase64()
            TinkConfig.initAead()
            val decodedData = keysetManager.transformAssociatedDataToWorkInstance(
                bytesIn = bytes,
                encryptionMode = false,
                authenticationTag = Args.TAG_ASSOCIATED_DATA_TRANSPORT
            )
            keysetManager.setAssociatedDataExplicitly(decodedData)
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