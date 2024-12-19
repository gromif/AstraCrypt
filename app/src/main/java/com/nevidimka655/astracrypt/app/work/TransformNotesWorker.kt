package com.nevidimka655.astracrypt.app.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.crypto.tink.Aead
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.utils.Api
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.data.database.RepositoryEncryption
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.data.TinkConfig
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@HiltWorker
class TransformNotesWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val repositoryEncryption: RepositoryEncryption,
    private val keysetManager: KeysetManager,
) : CoroutineWorker(appContext, params) {

    object Args {
        const val oldEncryptionInfo = ImportFilesWorker.Args.associatedData
        const val newEncryptionInfo = ImportFilesWorker.Args.fileWithUris
        const val associatedData = "the_royal_palace"
        const val TAG_ASSOCIATED_DATA_TRANSPORT = "the_city_of_light"
    }

    private var fromPrimitive: Aead? = null
    private var toPrimitive: Aead? = null
    private val isEncryptionDifferent by lazy { fromEncryption != toEncryption }

    private val oldAeadInfo by lazy {
        Json.decodeFromString<AeadInfo>(inputData.getString(Args.oldEncryptionInfo)!!)
    }
    private val newAeadInfo by lazy {
        Json.decodeFromString<AeadInfo>(inputData.getString(Args.newEncryptionInfo)!!)
    }
    private val fromEncryption get() = oldAeadInfo.aeadNotes
    private val toEncryption get() = newAeadInfo.aeadNotes
    private var notificationId = 3

    override suspend fun doWork() = withContext(defaultDispatcher) {
        setForeground(getForegroundInfo())
        initEncryption()
        shouldDecodeAssociatedData()
        var pageIndex = 0
        val pageSize = 20
        /*var itemsList = repository.getTransformNotesItems(pageSize, pageIndex)
        while (itemsList.isNotEmpty()) {
            val encrypt = toPrimitive != null
            val isEncrypted = fromPrimitive != null
            itemsList.forEach {
                val name = doString(encrypt, isEncrypted, it.name)
                val text = doString(encrypt, isEncrypted, it.text)
                val textPreview = doString(encrypt, isEncrypted, it.textPreview)
                repository.updateTransformNotes(
                    id = it.id, name = name, text = text, textPreview = textPreview
                )
            }
            pageIndex++
            itemsList = repository.getTransformNotesItems(pageSize, pageIndex)
        }*/
        delay(500)
        Result.success()
    }

    private suspend fun shouldDecodeAssociatedData() {
        if (newAeadInfo.bindAssociatedData) {
            val bytes = inputData.getString(Args.associatedData)!!.fromBase64()
            val decodedData = keysetManager.transformAssociatedDataToWorkInstance(
                bytesIn = bytes,
                encryptionMode = false,
                authenticationTag = Args.TAG_ASSOCIATED_DATA_TRANSPORT
            )
            keysetManager.setAssociatedDataExplicitly(decodedData)
        }
    }

    private suspend fun initEncryption() {
        TinkConfig.initAead()
        val keysetHandleFrom = fromEncryption?.let {
            keysetManager.aead(it)
        }
        val keysetHandleTo = toEncryption?.let {
            keysetManager.aead(it)
        }
        fromPrimitive = keysetHandleFrom?.aeadPrimitive()
        toPrimitive = keysetHandleTo?.aeadPrimitive()
    }

    private suspend fun doString(encrypt: Boolean, encrypted: Boolean, value: String?) = value?.run {
        if (encrypt) {
            if (!encrypted || isEncryptionDifferent) {
                val nameToEncrypt = if (encrypted) decrypt(this) else this
                encrypt(nameToEncrypt)
            } else this
        } else {
            if (encrypted) decrypt(this) else this
        }
    } ?: value

    private suspend fun encrypt(str: String) = if (str.isNotEmpty())
        toPrimitive?.run { repositoryEncryption.encrypt(str) }
            ?: str else str

    private fun decrypt(str: String) = if (str.isNotEmpty())
        fromPrimitive?.run { repositoryEncryption.decryptString(this, str) }
            ?: str else str

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val channelId = applicationContext.getString(
            R.string.notification_channel_fileOperations_id
        )
        val title = applicationContext.getString(R.string.notification_dbTransform_title)
        // Create a Notification channel if necessary
        if (Api.atLeast8()) {
            createChannel()
        }
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_DEFAULT)
            .setTicker(title)
            .setProgress(100, 0, true)
            .setSmallIcon(R.drawable.ic_notification_app_icon)
            .setOngoing(true)
            .setSilent(true)
            .build()
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
        val channelId =
            applicationContext.getString(R.string.notification_channel_fileOperations_id)
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        NotificationManagerCompat.from(applicationContext).createNotificationChannel(channel)
    }

}