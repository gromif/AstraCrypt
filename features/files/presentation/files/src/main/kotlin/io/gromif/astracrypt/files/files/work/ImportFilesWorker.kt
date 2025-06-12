package io.gromif.astracrypt.files.files.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.gromif.astracrypt.files.domain.usecase.actions.ImportUseCase
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.utils.Api
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.random.Random

@HiltWorker
class ImportFilesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val importUseCase: ImportUseCase,
) : CoroutineWorker(context, params) {

    object Args {
        const val URI_FILE = "a1"
        const val PARENT_ID = "a2"
        const val SAVE_SOURCE = "a3"
    }

    override suspend fun doWork(): Result {
        val parentId = inputData.getLong(Args.PARENT_ID, 0)
        val saveSource = inputData.getBoolean(Args.SAVE_SOURCE, false)
        val uriFile = File(inputData.getString(Args.URI_FILE)!!)
        setForeground(getForegroundInfo())
        withContext(defaultDispatcher.limitedParallelism(4)) {
            val pathList = uriFile.readLines()
            importUseCase(
                pathList = pathList,
                parentId = parentId,
                saveSource = saveSource
            )
        }
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val title = applicationContext.getString(R.string.notification_import_title)
        val cancelText = applicationContext.getString(android.R.string.cancel)
        // This PendingIntent can be used to cancel the worker
        val workerStopPendingIntent =
            WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
        // Create a Notification channel if necessary
        if (Api.atLeast8()) createChannel()
        val notification = NotificationCompat.Builder(
            applicationContext,
            NOTIFICATION_CHANNEL_ID
        ).apply {
            setContentTitle(title)
            setTicker(title)
            setProgress(100, 0, true)
            setSmallIcon(R.drawable.ic_notification_app_icon)
            setOngoing(true)
            setSilent(true)
            addAction(R.drawable.ic_close, cancelText, workerStopPendingIntent)
        }.build()
        val notificationId = Random.Default.nextInt()
        return if (Api.atLeast10()) {
            ForegroundInfo(
                notificationId,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            ForegroundInfo(notificationId, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val name = applicationContext.getString(R.string.notification_channel_fileOperations)
        val summary =
            applicationContext.getString(R.string.notification_channel_fileOperations_desc)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
            description = summary
        }
        // Register the channel with the system
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private const val NOTIFICATION_CHANNEL_ID = "file_operations_channel"