package com.nevidimka655.astracrypt.work

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
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.room.DatabaseTransformTuple
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.RepositoryEncryption
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.utils.enums.DatabaseColumns
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.TinkConfig
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlin.random.Random

@HiltWorker
class TransformDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: Repository,
    private val repositoryEncryption: RepositoryEncryption,
    private val keysetFactory: KeysetFactory
) : CoroutineWorker(appContext, params) {

    object Args {
        const val oldEncryptionInfo = ImportFilesWorker.Args.encryptionInfo
        const val newEncryptionInfo = "fe"
        const val associatedData = "query"
        const val TAG_ASSOCIATED_DATA_TRANSPORT = "columns"
    }

    private var fromPrimitive: Aead? = null
    private var toPrimitive: Aead? = null
    private var fromKeysetHandleKeyId = 0
    private var toKeysetHandleKeyId = 0
    private val isEncryptionDifferent by lazy { fromEncryption != toEncryption }

    private val oldEncryptionInfo by lazy {
        Json.decodeFromString<EncryptionInfo>(inputData.getString(Args.oldEncryptionInfo)!!)
    }
    private val newEncryptionInfo by lazy {
        Json.decodeFromString<EncryptionInfo>(inputData.getString(Args.newEncryptionInfo)!!)
    }
    private val fromEncryption get() = oldEncryptionInfo.databaseEncryptionOrdinal
    private val toEncryption get() = newEncryptionInfo.databaseEncryptionOrdinal
    private val isNameEncrypted get() = oldEncryptionInfo.isNameEncrypted
    private val isThumbEncrypted get() = oldEncryptionInfo.isThumbnailEncrypted
    private val isPathEncrypted get() = oldEncryptionInfo.isPathEncrypted
    private val isFlagsEncrypted get() = oldEncryptionInfo.isFlagsEncrypted
    private val isEncryptionTypeEncrypted get() = oldEncryptionInfo.isEncryptionTypeEncrypted
    private val isThumbEncryptionTypeEncrypted get() = oldEncryptionInfo.isThumbEncryptionTypeEncrypted

    private val encryptName get() = newEncryptionInfo.isNameEncrypted
    private val encryptThumb get() = newEncryptionInfo.isThumbnailEncrypted
    private val encryptPath get() = newEncryptionInfo.isPathEncrypted
    private val encryptDetails get() = newEncryptionInfo.isFlagsEncrypted
    private val encryptEncryptionType get() = newEncryptionInfo.isEncryptionTypeEncrypted
    private val encryptThumbEncryptionType get() = newEncryptionInfo.isThumbEncryptionTypeEncrypted

    private var notificationId = 0

    override suspend fun doWork(): Result {
        notificationId = Random.nextInt(0, 101)
        setForeground(getForegroundInfo())
        withContext(Dispatchers.IO) {
            initEncryption()
            shouldDecodeAssociatedData()
            var pageIndex = 0
            val pageSize = 20
            var itemsList = repository.getDatabaseTransformItems(pageSize, pageIndex)
            while (itemsList.isNotEmpty()) {
                iterateDatabaseItems(itemsList)
                pageIndex++
                itemsList = repository.getDatabaseTransformItems(pageSize, pageIndex)
            }
        }
        delay(500)
        return Result.success()
    }

    private fun shouldDecodeAssociatedData() {
        if (newEncryptionInfo.isAssociatedDataEncrypted) {
            val bytes = inputData.getString(Args.associatedData)!!.fromBase64()
            val decodedData = keysetFactory.transformAssociatedDataToWorkInstance(
                bytesIn = bytes,
                encryptionMode = false,
                authenticationTag = Args.TAG_ASSOCIATED_DATA_TRANSPORT
            )
            keysetFactory.setAssociatedDataExplicitly(decodedData)
        }
    }

    private fun initEncryption() {
        TinkConfig.initAead()
        val keysetHandleFrom = if (fromEncryption > -1) {
            keysetFactory.aead(KeysetTemplates.AEAD.entries[fromEncryption])
        } else null
        val keysetHandleTo = if (toEncryption > -1) {
            keysetFactory.aead(KeysetTemplates.AEAD.entries[toEncryption])
        } else null
        fromKeysetHandleKeyId = keysetHandleFrom?.primary?.id ?: 0
        toKeysetHandleKeyId = keysetHandleTo?.primary?.id ?: 0

        fromPrimitive = keysetHandleFrom?.aeadPrimitive()
        toPrimitive = keysetHandleTo?.aeadPrimitive()
    }

    private suspend fun iterateDatabaseItems(list: List<DatabaseTransformTuple>) = list.forEach {
        val name = operateStringField(encryptName, isNameEncrypted, it.name)
        val thumbnail = operateStringField(encryptThumb, isThumbEncrypted, it.thumb)
        val path = operateStringField(encryptPath, isPathEncrypted, it.path)
        val encryptionType = operateIntField(
            associatedInt = it.id.toInt() * DatabaseColumns.EncryptionType.ordinal,
            encrypt = encryptEncryptionType,
            encrypted = isEncryptionTypeEncrypted,
            value = it.encryptionType
        )
        val thumbEncryptionType = operateIntField(
            associatedInt = it.id.toInt() * DatabaseColumns.ThumbEncryptionType.ordinal,
            encrypt = encryptThumbEncryptionType,
            encrypted = isThumbEncryptionTypeEncrypted,
            value = it.thumbnailEncryptionType
        )
        val details = operateStringField(encryptDetails, isFlagsEncrypted, it.flags)
        repository.updateDbEntry(
            id = it.id,
            name = name,
            thumb = thumbnail,
            path = path,
            encryptionType = encryptionType,
            thumbEncryptionType = thumbEncryptionType,
            flags = details
        )
    }

    private fun operateStringField(
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

    private fun operateIntField(
        associatedInt: Int,
        encrypt: Boolean,
        encrypted: Boolean,
        value: Int
    ) = if (encrypt && toEncryption != -1) {
        if (!encrypted || isEncryptionDifferent) {
            val valueToEncrypt = if (fromEncryption == -1 || !encrypted) value
            else repositoryEncryption.decryptIntField(fromKeysetHandleKeyId, associatedInt, value)
            repositoryEncryption.encryptIntField(toKeysetHandleKeyId, associatedInt, valueToEncrypt)
        } else value
    } else {
        if (encrypted) repositoryEncryption.decryptIntField(
            fromKeysetHandleKeyId,
            associatedInt,
            value
        )
        else value
    }

    private fun encrypt(str: String) = if (str.isNotEmpty())
        toPrimitive?.run { repositoryEncryption.encryptStringField(this, str) }
            ?: str else str

    private fun decrypt(str: String) = if (str.isNotEmpty())
        fromPrimitive?.run { repositoryEncryption.decryptStringField(this, str) }
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