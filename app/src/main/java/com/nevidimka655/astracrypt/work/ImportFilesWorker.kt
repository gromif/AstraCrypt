package com.nevidimka655.astracrypt.work

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.provider.DocumentsContractCompat
import androidx.documentfile.provider.DocumentFile
import androidx.exifinterface.media.ExifInterface
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ImageRequest
import com.google.crypto.tink.StreamingAead
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.entities.EncryptionInfo
import com.nevidimka655.astracrypt.entities.StorageItemFlags
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.utils.*
import com.nevidimka655.astracrypt.utils.enums.StorageItemType
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.TinkConfig
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.secureRandom
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.OutputStream

@HiltWorker
class ImportFilesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val imageLoader: ImageLoader,
    private val defaultCoilRequestBuilder: ImageRequest.Builder
) : CoroutineWorker(context, params) {
    private val defaultDispatcher = Dispatchers.IO
    private val contentResolver get() = applicationContext.contentResolver

    object Args {
        const val fileWithUris = "Be_My_Eyes"
        const val dirId = "01"
        const val saveOriginalFiles = "sad"
        const val encryptionInfo = "material"
        const val associatedData = "keyset"
        const val TAG_ASSOCIATED_DATA_TRANSPORT = "keyset2"
    }

    private val encryptionInfo: EncryptionInfo by lazy {
        Json.decodeFromString(inputData.getString(Args.encryptionInfo)!!)
    }
    private val fileEncryption get() = encryptionInfo.fileEncryptionOrdinal
    private val thumbEncryption get() = encryptionInfo.thumbEncryptionOrdinal
    private val bitmapOptions = BitmapFactory.Options().apply { inJustDecodeBounds = true }

    private var saveOriginalFiles = false
    private var parentDirectoryId: Long = 0

    private var notificationId = 0

    override suspend fun doWork(): Result {
        parentDirectoryId = inputData.getLong(Args.dirId, 0)
        saveOriginalFiles = inputData.getBoolean(Args.saveOriginalFiles, false)
        setForeground(getForegroundInfo())
        TinkConfig.initStream()
        shouldDecodeAssociatedData()
        val keysetHandleForFileEncryption = if (fileEncryption > -1) {
            KeysetFactory.stream(applicationContext, KeysetTemplates.Stream.entries[fileEncryption])
        } else null
        val keysetHandleForThumbEncryption = if (thumbEncryption > -1) {
            KeysetFactory.stream(applicationContext, KeysetTemplates.Stream.entries[thumbEncryption])
        } else null
        val fileEncryptionPrimitive = keysetHandleForFileEncryption?.streamingAeadPrimitive()
        val thumbEncryptionPrimitive = keysetHandleForThumbEncryption?.streamingAeadPrimitive()
        val file = File(inputData.getString(Args.fileWithUris)!!)
        val urisList = withContext(defaultDispatcher) { file.readLines() }
        file.delete()
        var nextId = withContext(defaultDispatcher) { Repository.getMaxId() }
        try {
            urisList.forEach {
                yield()
                if (isStopped) return@forEach
                val itemId = nextId + 1
                itemListIterator(
                    dbItemId = itemId,
                    fileUri = Uri.parse(it),
                    filePrimitive = fileEncryptionPrimitive,
                    thumbPrimitive = thumbEncryptionPrimitive
                )
                nextId++
            }
        } catch (ignored: Exception) {}
        return Result.success()
    }

    private fun shouldDecodeAssociatedData() {
        if (encryptionInfo.isAssociatedDataEncrypted) {
            TinkConfig.initAead()
            val bytes = inputData.getString(Args.associatedData)!!.fromBase64()
            val decodedData = KeysetFactory.transformAssociatedDataToWorkInstance(
                context = applicationContext,
                bytesIn = bytes,
                encryptionMode = false,
                authenticationTag = Args.TAG_ASSOCIATED_DATA_TRANSPORT
            )
            KeysetFactory.setAssociatedDataExplicitly(decodedData)
        }
    }

    private suspend fun itemListIterator(
        dbItemId: Long,
        fileUri: Uri,
        filePrimitive: StreamingAead?,
        thumbPrimitive: StreamingAead?
    ) = coroutineScope {
        val docFile = DocumentFile.fromSingleUri(applicationContext, fileUri)
            ?: return@coroutineScope
        val fileName = docFile.name ?: return@coroutineScope
        val docType = docFile.type
        val itemType = if (docType != null) {
            parseUriFileType(docType)
        } else StorageItemType.Photo
        val thumbnailPath = async(defaultDispatcher) {
            tryToSaveThumbnail(
                uri = fileUri,
                primitive = thumbPrimitive,
                itemType = itemType
            )
        }
        val randomNum = getRandomDirectory(fileUri.toString())
        val randomFileName = Randomizer.getUrlSafeString(
            secureRandom(fileUri.toString().toByteArray()), AppConfig.DB_FILE_NAME_COUNT
        )
        val outRelativePath = "$randomNum/$randomFileName"
        val outFile = File("${IO.dataDir}/$outRelativePath")
        contentResolver.openInputStream(fileUri)!!.use { inStream ->
            filePrimitive?.newEncryptingStream(
                outFile.outputStream(), KeysetFactory.associatedData
            )?.use { writeData(inStream, it) } ?: outFile.outputStream().use {
                writeData(inStream, it)
            }
        }
        if (!isStopped) {
            var creationDate = docFile.lastModified()
            if (creationDate == 0L) creationDate = System.currentTimeMillis()
            val item = StorageItemEntity(
                id = dbItemId,
                name = fileName,
                itemType = itemType,
                parentDirectoryId = parentDirectoryId,
                originalSizeInBytes = docFile.length(),
                thumb = thumbnailPath.await(),
                path = outRelativePath,
                flags = getFlags(itemType, fileUri),
                encryptionType = fileEncryption,
                creationTime = creationDate,
                thumbnailEncryptionType = thumbEncryption
            )
            Repository.insert(encryptionInfo, item)
            if (!saveOriginalFiles) try {
                val isFileDeleted = docFile.delete()
                if (isFileDeleted) return@coroutineScope
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val `in` = Bundle()
                    `in`.putParcelable("uri", fileUri)
                    contentResolver.call(
                        fileUri.authority!!,
                        "android:deleteDocument", null, `in`
                    )
                } else docFile.parentFile?.let { parent ->
                    DocumentsContractCompat.removeDocument(contentResolver, fileUri, parent.uri)
                }
            } catch (e: Exception) {}
        } else {
            outFile.delete()
            File("${IO.dataDir}/$thumbnailPath").delete()
        }
    }

    @SuppressLint("NewApi")
    override suspend fun getForegroundInfo(): ForegroundInfo {
        val channelId = applicationContext.getString(
            R.string.notification_channel_fileOperations_id
        )
        val title = applicationContext.getString(R.string.notification_import_title)
        val cancelText = applicationContext.getString(android.R.string.cancel)
        // This PendingIntent can be used to cancel the worker
        val workerStopPendingIntent =
            WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
        // Create a Notification channel if necessary
        if (Api.atLeastAndroid8()) createChannel()
        val notification = NotificationCompat.Builder(applicationContext, channelId).apply {
            setContentTitle(title)
            setTicker(title)
            setProgress(100, 0, true)
            setSmallIcon(R.drawable.ic_notification_app_icon)
            setOngoing(true)
            setSilent(true)
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
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun parseUriFileType(type: String) = when {
        type.startsWith("image") -> StorageItemType.Photo
        type.startsWith("audio") -> StorageItemType.Music
        type.startsWith("video") -> StorageItemType.Video
        type.startsWith("text") -> StorageItemType.Text
        else -> StorageItemType.Other
    }

    @SuppressLint("NewApi")
    private suspend fun tryToSaveThumbnail(
        uri: Uri,
        primitive: StreamingAead?,
        itemType: StorageItemType
    ): String {
        val bitmap = try {
            when (itemType) {
                StorageItemType.Music -> getMediaMetadataRetrieverCompat(uri).use { retriever ->
                    retriever.embeddedPicture?.let {
                        val request = defaultCoilRequestBuilder.data(it).build()
                        val bitmapDrawable =
                            imageLoader.execute(request).drawable as BitmapDrawable?
                        bitmapDrawable?.bitmap
                    }
                }
                else -> {
                    val request = defaultCoilRequestBuilder.data(uri).build()
                    val bitmapDrawable = imageLoader.execute(request).drawable as BitmapDrawable?
                    bitmapDrawable?.bitmap
                }
            }
        } catch (e: Exception) {
            e.toString()
            null
        } ?: return ""
        val secureSeed = uri.toString()
        val randomDir = getRandomDirectory(secureSeed)
        val thumbnailFileName = Randomizer.getUrlSafeString(
            secureRandom(secureSeed.toByteArray()), AppConfig.DB_THUMB_FILE_NAME_COUNT
        )
        val bitmapCompressFormat = if (Api.atLeastAndroid11()) {
            Bitmap.CompressFormat.WEBP_LOSSY
        } else Bitmap.CompressFormat.WEBP
        val compressedByteStream = ByteArrayOutputStream().also {
            bitmap.compress(
                bitmapCompressFormat,
                AppConfig.DB_THUMB_QUALITY,
                it
            )
        }
        val relativePath = "$randomDir/$thumbnailFileName"
        val fileOut = File("${IO.dataDir}/$relativePath")
        primitive?.newEncryptingStream(
            fileOut.outputStream(),
            KeysetFactory.associatedData
        )?.use {
            it.write(compressedByteStream.toByteArray())
        } ?: fileOut.outputStream().use { it.write(compressedByteStream.toByteArray()) }
        return relativePath
    }

    private fun getFlags(
        type: StorageItemType,
        fileUri: Uri
    ): String {
        val inFileStream = applicationContext.contentResolver.openInputStream(fileUri)!!
        val flags = when (type) {
            StorageItemType.Photo -> getImageFlags(fileUri, inFileStream)
            StorageItemType.Video -> getVideoFlags(fileUri)
            StorageItemType.Music -> getMusicFlags(fileUri)
            else -> null
        }
        inFileStream.close()
        return if (flags != null) {
            Json.encodeToString(flags)
        } else ""
    }

    private fun getImageFlags(fileUri: Uri, inFileStream: InputStream): StorageItemFlags {
        val imageFlags = StorageItemFlags.Image()
        var height: Int
        var width: Int
        ExifInterface(inFileStream).run {
            width = getAttribute(ExifInterface.TAG_IMAGE_WIDTH)?.toIntOrNull() ?: 0
            height = getAttribute(ExifInterface.TAG_IMAGE_LENGTH)?.toIntOrNull() ?: 0
        }
        if (height + width == 0) {
            applicationContext.contentResolver.openFileDescriptor(fileUri, "r")!!.use {
                BitmapFactory.decodeFileDescriptor(
                    it.fileDescriptor,
                    null,
                    bitmapOptions
                )
            }
            height = bitmapOptions.outHeight
            width = bitmapOptions.outWidth
        }
        return imageFlags.apply {
            resolution = "${width}x$height"
        }
    }

    private fun getVideoFlags(fileUri: Uri): StorageItemFlags {
        val videoFlags = StorageItemFlags.Video()
        getMediaMetadataRetrieverCompat(fileUri).use {
            val width = it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
            val height = it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
            videoFlags.resolution = "${width}x$height"
        }
        return videoFlags
    }

    @SuppressLint("InlinedApi")
    private fun getMusicFlags(fileUri: Uri): StorageItemFlags {
        val musicFlags = StorageItemFlags.Music()
        applicationContext.contentResolver.openFileDescriptor(
            fileUri,
            "r"
        )!!.use {
            MediaExtractor().apply {
                setDataSource(
                    it.fileDescriptor
                )
            }.getTrackFormat(0).run {
                musicFlags.sampleRate = getInteger(MediaFormat.KEY_SAMPLE_RATE)
            }
        }
        return musicFlags
    }

    private fun getMediaMetadataRetrieverCompat(fileUri: Uri) =
        MediaMetadataRetrieverCompat().apply { setDataSource(applicationContext, fileUri) }

    private fun getRandomDirectory(seed: String): String {
        val randomNum =
            secureRandom(seed.toByteArray()).nextInt(1, AppConfig.DB_DIRS_COUNT + 1)
        val randomDirFile = File("${IO.dataDir}/$randomNum")
        if (!randomDirFile.exists()) randomDirFile.mkdir()
        return randomNum.toString()
    }

    private fun writeData(input: InputStream, out: OutputStream) {
        val buffer = ByteArray(4096)
        var loadedSize = input.read(buffer)
        while (loadedSize != -1) {
            if (isStopped) return
            out.write(buffer, 0, loadedSize)
            loadedSize = input.read(buffer)
        }
    }
}