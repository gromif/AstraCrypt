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
import com.nevidimka655.astracrypt.domain.repository.files.FilesRepository
import com.nevidimka655.astracrypt.domain.database.DatabaseTransformTuple
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
import kotlin.random.Random

@HiltWorker
class TransformDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val filesRepository: FilesRepository,
    private val repositoryEncryption: RepositoryEncryption,
    private val keysetManager: KeysetManager
) : CoroutineWorker(appContext, params) {

    object Args {
        const val oldEncryptionInfo = ImportFilesWorker.Args.aeadInfo
        const val newEncryptionInfo = "fe"
        const val associatedData = "query"
        const val TAG_ASSOCIATED_DATA_TRANSPORT = "columns"
    }

    private var fromPrimitive: Aead? = null
    private var toPrimitive: Aead? = null
    private var fromKeysetHandleKeyId = 0
    private var toKeysetHandleKeyId = 0
    private val isEncryptionDifferent by lazy { fromEncryption != toEncryption }

    private val oldAeadInfo by lazy {
        Json.decodeFromString<AeadInfo>(inputData.getString(Args.oldEncryptionInfo)!!)
    }
    private val newAeadInfo by lazy {
        Json.decodeFromString<AeadInfo>(inputData.getString(Args.newEncryptionInfo)!!)
    }
    private val fromEncryption get() = oldAeadInfo.database
    private val toEncryption get() = newAeadInfo.database
    private val isNameEncrypted get() = oldAeadInfo.name
    private val isThumbEncrypted get() = oldAeadInfo.thumb
    private val isPathEncrypted get() = oldAeadInfo.path
    private val isFlagsEncrypted get() = oldAeadInfo.flags

    private val encryptName get() = newAeadInfo.name
    private val encryptThumb get() = newAeadInfo.thumb
    private val encryptPath get() = newAeadInfo.path
    private val encryptDetails get() = newAeadInfo.flags

    private var notificationId = 0

    override suspend fun doWork(): Result {
        notificationId = Random.nextInt(0, 101)
        setForeground(getForegroundInfo())
        withContext(defaultDispatcher) {
            initEncryption()
            shouldDecodeAssociatedData()
            var pageIndex = 0
            val pageSize = 20
            var itemsList = filesRepository.getDatabaseTransformItems(pageSize, pageIndex)
            while (itemsList.isNotEmpty()) {
                iterateDatabaseItems(itemsList)
                pageIndex++
                itemsList = filesRepository.getDatabaseTransformItems(pageSize, pageIndex)
            }
        }
        delay(500)
        return Result.success()
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
        val keysetHandleFrom = fromEncryption?.let { keysetManager.aead(it.aead) }
        val keysetHandleTo = toEncryption?.let { keysetManager.aead(it.aead) }
        fromKeysetHandleKeyId = keysetHandleFrom?.primary?.id ?: 0
        toKeysetHandleKeyId = keysetHandleTo?.primary?.id ?: 0

        fromPrimitive = keysetHandleFrom?.aeadPrimitive()
        toPrimitive = keysetHandleTo?.aeadPrimitive()
    }

    private suspend fun iterateDatabaseItems(list: List<DatabaseTransformTuple>) = list.forEach {
        val name = operateStringField(encryptName, isNameEncrypted, it.name)
        val thumbnail = operateStringField(encryptThumb, isThumbEncrypted, it.preview)
        val path = operateStringField(encryptPath, isPathEncrypted, it.path)
        val details = operateStringField(encryptDetails, isFlagsEncrypted, it.flags)
        filesRepository.updateDbEntry(
            id = it.id,
            name = name,
            thumb = thumbnail,
            path = path,
            flags = details
        )
    }

    private suspend fun operateStringField(
        encrypt: Boolean,
        encrypted: Boolean,
        value: String,
    ) = if (encrypt) {
        if (!encrypted || isEncryptionDifferent) {
            val nameToEncrypt = if (encrypted) decrypt(value) else value
            encrypt(nameToEncrypt)
        } else value
    } else {
        if (encrypted) decrypt(value) else value
    }

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