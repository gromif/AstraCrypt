package com.nevidimka655.astracrypt.features.details

import android.text.format.DateFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ChangeCircle
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PhotoSizeSelectLarge
import androidx.compose.material.icons.outlined.SdCard
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.model.StorageItemFlags
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.compose_details.DetailsManager
import com.nevidimka655.compose_details.addItem
import com.nevidimka655.compose_details.entities.DetailsItem
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.ui.compose_core.wrappers.IconWrap
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val io: Io,
    val detailsManager: DetailsManager,
    val imageLoader: ImageLoader
): ViewModel() {

    suspend fun submitDetailsQuery(
        encryptionInfo: EncryptionInfo,
        itemId: Long
    ) = detailsManager.run {
        val item = Repository.getById(encryptionInfo, itemId)
        val absolutePath = Repository.getAbsolutePath(
            encryptionInfo = encryptionInfo,
            childName = item.name,
            parentDirId = item.parentDirectoryId
        )
        val isFile = item.itemType.isFile
        title = item.name
        extras = bundleOf(
            "encryption" to item.thumbnailEncryptionType,
            "isFile" to isFile,
            "thumb" to item.thumb
        )

        addGroup(name = TextWrap(id = R.string.files_options_details)) {
            addItem(
                icon = IconWrap(imageVector = Icons.AutoMirrored.Outlined.InsertDriveFile),
                title = TextWrap(id = R.string.itemType),
                summary = TextWrap(id = item.itemType.title)
            )
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.FolderOpen),
                title = TextWrap(id = R.string.path),
                summary = TextWrap(text = absolutePath)
            )
            // SIZE
            if (isFile) {
                val file = io.getLocalFile(item.path)
                val currentSizeBytes = file.length()
                val currentSize = io.bytesToHumanReadable(currentSizeBytes)
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.SdCard),
                    title = TextWrap(id = R.string.currentSize),
                    summary = TextWrap(text = "$currentSize ($currentSizeBytes B)")
                )
            }
            // TIME
            val pattern = "d MMMM yyyy"
            val creationTime = DateFormat.format(pattern, item.creationTime).toString()
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.AccessTime),
                title = TextWrap(id = R.string.creationTime),
                summary = TextWrap(text = creationTime)
            )
            if (item.modificationTime.toInt() != 0) {
                val modificationTime = DateFormat.format(pattern, item.modificationTime).toString()
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.ChangeCircle),
                    title = TextWrap(id = R.string.modificationTime),
                    summary = TextWrap(text = modificationTime)
                )
            }
        }
        if (isFile) {
            if (item.flags.isNotEmpty()) {
                addGroup(name = TextWrap(id = item.itemType.title)) {
                    when (val flags = Json.decodeFromString<StorageItemFlags>(item.flags)) {
                        is StorageItemFlags.App -> flags.toString()
                        is StorageItemFlags.Image -> addImageFlags(flags)
                        is StorageItemFlags.Music -> addMusicFlags(flags)
                        is StorageItemFlags.Video -> addVideoFlags(flags)
                    }
                }
            }
            addGroup(name = TextWrap(id = R.string.settings_encryption)) {
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.Lock),
                    title = TextWrap(id = R.string.encryption_type),
                    summary = if (item.encryptionType == -1) {
                        TextWrap(id = R.string.withoutEncryption)
                    } else TextWrap(text = KeysetTemplates.Stream.entries[item.encryptionType].name)
                )
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.Lock),
                    title = TextWrap(id = R.string.thumb_encryption_type),
                    summary = if (item.thumbnailEncryptionType == -1) {
                        TextWrap(id = R.string.withoutEncryption)
                    } else TextWrap(text = KeysetTemplates.Stream.entries[item.thumbnailEncryptionType].name)
                )
            }
        } else {
            val content = Repository.getFolderContent(item.id)
            addGroup(name = TextWrap(id = R.string.folder)) {
                val files = Engine.appContext.getString(R.string.files)
                val folders = Engine.appContext.getString(R.string.folders)
                val summaryText = "$files - ${content.filesCount}, $folders - ${content.foldersCount}"
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.Folder),
                    title = TextWrap(id = R.string.folderContents),
                    summary = TextWrap(text = summaryText)
                )
            }
        }
        if (isFile) addGroup(name = TextWrap(id = R.string.settings_security)) {
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.FolderOpen),
                title = TextWrap(id = R.string.path),
                summary = TextWrap(text = item.path)
            )
            val originalSizeBytes = item.originalSizeInBytes
            val originalSize = io.bytesToHumanReadable(originalSizeBytes)
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.SdCard),
                title = TextWrap(id = R.string.originalSize),
                summary = TextWrap(text = "$originalSize ($originalSizeBytes B)")
            )
            if (item.thumb.isNotEmpty()) {
                val thumbnailFile = io.getLocalFile(item.thumb)
                val thumbnailSizeBytes = thumbnailFile.length()
                val thumbnailSize = io.bytesToHumanReadable(thumbnailSizeBytes)
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.FolderOpen),
                    title = TextWrap(id = R.string.thumbnailPath),
                    summary = TextWrap(text = item.thumb)
                )
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.SdCard),
                    title = TextWrap(id = R.string.thumbnailSize),
                    summary = TextWrap(text = "$thumbnailSize ($thumbnailSizeBytes B)")
                )
            }
        }
    }

    private fun MutableList<DetailsItem>.addImageFlags(flags: StorageItemFlags.Image) {
        addItem(
            icon = IconWrap(imageVector = Icons.Outlined.PhotoSizeSelectLarge),
            title = TextWrap(id = R.string.imageResolution),
            summary = TextWrap(text = flags.resolution)
        )
    }

    private fun MutableList<DetailsItem>.addMusicFlags(flags: StorageItemFlags.Music) {
        addItem(
            icon = IconWrap(imageVector = Icons.Outlined.Headphones),
            title = TextWrap(id = R.string.sample_rate),
            summary = TextWrap(text = "${flags.sampleRate}kHz")
        )
    }

    private fun MutableList<DetailsItem>.addVideoFlags(flags: StorageItemFlags.Video) {
        addItem(
            icon = IconWrap(imageVector = Icons.Outlined.PhotoSizeSelectLarge),
            title = TextWrap(id = R.string.videoResolution),
            summary = TextWrap(text = flags.resolution)
        )
    }

}