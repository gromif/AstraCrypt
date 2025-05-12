package io.gromif.notes.presentation.settings.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.utils.Api
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.usecase.UpdateNotesAeadUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.random.Random

@HiltWorker
internal class UpdateAeadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val updateNotesAeadUseCase: UpdateNotesAeadUseCase,
) : CoroutineWorker(context, params) {

    companion object {
        private const val TARGET_AEAD = "a1"

        fun createWorkerData(targetAead: AeadMode): Data {
            val targetAead = targetAead.id
            val data = workDataOf(
                TARGET_AEAD to targetAead,
            )
            return data
        }

    }

    override suspend fun doWork(): Result {
        val targetAeadMode = inputData.getInt(TARGET_AEAD, -1).let {
            if (it == -1) AeadMode.None else AeadMode.Template(it, "")
        }
        setForeground(getForegroundInfo())
        withContext(defaultDispatcher.limitedParallelism(4)) {
            updateNotesAeadUseCase(targetAeadMode)
        }
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val title = applicationContext.getString(R.string.notification_dbTransform_title)
        val cancelText = applicationContext.getString(android.R.string.cancel)
        // This PendingIntent can be used to cancel the worker
        val workerStopPendingIntent =
            WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
        // Create a Notification channel if necessary
        if (Api.atLeast8()) createChannel()
        val notification = NotificationCompat.Builder(
            applicationContext, NOTIFICATION_CHANNEL_ID
        ).apply {
            setContentTitle(title)
            setTicker(title)
            setProgress(100, 0, true)
            setSmallIcon(R.drawable.ic_notification_app_icon)
            setOngoing(true)
            setSilent(true)
            addAction(R.drawable.ic_close, cancelText, workerStopPendingIntent)
        }.build()
        val notificationId = Random.nextInt()
        return if (Api.atLeast10()) ForegroundInfo(
            notificationId,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        ) else ForegroundInfo(notificationId, notification)
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