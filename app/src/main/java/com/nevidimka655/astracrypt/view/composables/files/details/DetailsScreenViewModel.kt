package com.nevidimka655.astracrypt.view.composables.files.details

import androidx.lifecycle.ViewModel
import com.nevidimka655.astracrypt.domain.usecase.BytesToHumanReadableUseCase
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.compose_details.DetailsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    //private val repository: Repository,
    private val filesUtil: FilesUtil,
    private val bytesToHumanReadableUseCase: BytesToHumanReadableUseCase,
    val detailsManager: DetailsManager,
    //val imageLoader: ImageLoader
): ViewModel() {
    /*var preview: String? = ""
    var type: FileTypes = FileTypes.Other

    suspend fun submitDetailsQuery(context: Context, itemId: Long) = detailsManager.run {
        val item = repository.getById(itemId)
        val absolutePath = repository.getAbsolutePath(
            childName = item.name,
            parentId = item.dirId
        )
        val isFile = item.type > 0
        type = FileTypes.entries[item.type]
        preview = item.preview
        title = item.name

        addGroup(name = TextWrap.Resource(id = R.string.files_options_details)) {
            *//*addItem(
                icon = IconWrap(imageVector = Icons.AutoMirrored.Outlined.InsertDriveFile),
                title = TextWrap.Resource(id = R.string.itemType),
                summary = TextWrap.Resource(id = item.type.title)
            )*//*
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.FolderOpen),
                title = TextWrap.Resource(id = R.string.path),
                summary = TextWrap.Text(text = absolutePath)
            )
            // SIZE
            if (isFile) {
                val file = filesUtil.getLocalFile(item.path)
                val currentSizeBytes = file.length()
                val currentSize = bytesToHumanReadableUseCase.invoke(currentSizeBytes)
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.SdCard),
                    title = TextWrap.Resource(id = R.string.currentSize),
                    summary = TextWrap.Text(text = "$currentSize ($currentSizeBytes B)")
                )
            }
            // TIME
            val pattern = "d MMMM yyyy"
            val creationTime = DateFormat.format(pattern, item.creationTime).toString()
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.AccessTime),
                title = TextWrap.Resource(id = R.string.creationTime),
                summary = TextWrap.Text(text = creationTime)
            )
            *//*if (item.modificationTime.toInt() != 0) {
                val modificationTime = DateFormat.format(pattern, item.modificationTime).toString()
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.ChangeCircle),
                    title = TextWrap.Resource(id = R.string.modificationTime),
                    summary = TextWrap.Text(text = modificationTime)
                )
            }*//*
        }
        if (isFile) {
            *//*if (item.flags.isNotEmpty()) {
                addGroup(name = TextWrap.Resource(id = item.type.title)) {
                    when (val flags = Json.decodeFromString<StorageFlags>(item.flags)) {
                        is StorageFlags.App -> flags.toString()
                        is StorageFlags.Image -> addImageFlags(flags)
                        is StorageFlags.Music -> addMusicFlags(flags)
                        is StorageFlags.Video -> addVideoFlags(flags)
                    }
                }
            }*//*
            addGroup(name = TextWrap.Resource(id = R.string.settings_encryption)) {
                *//*addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.Lock),
                    title = TextWrap.Resource(id = R.string.encryption_type),
                    summary = if (item.encryptionType == -1) {
                        TextWrap.Resource(id = R.string.withoutEncryption)
                    } else TextWrap.Text(text = KeysetTemplates.Stream.entries[item.encryptionType].name)
                )
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.Lock),
                    title = TextWrap.Resource(id = R.string.thumb_encryption_type),
                    summary = if (item.thumbnailEncryptionType == -1) {
                        TextWrap.Resource(id = R.string.withoutEncryption)
                    } else TextWrap.Text(text = KeysetTemplates.Stream.entries[item.thumbnailEncryptionType].name)
                )*//*
            }
        } else {
            val content = repository.getFolderContent(item.id)
            addGroup(name = TextWrap.Resource(id = R.string.folder)) {
                val files = context.getString(R.string.files)
                val folders = context.getString(R.string.folders)
                val summaryText = "$files - ${content.filesCount}, $folders - ${content.foldersCount}"
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.Folder),
                    title = TextWrap.Resource(id = R.string.folderContents),
                    summary = TextWrap.Text(text = summaryText)
                )
            }
        }
        if (isFile) addGroup(name = TextWrap.Resource(id = R.string.settings_security)) {
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.FolderOpen),
                title = TextWrap.Resource(id = R.string.path),
                summary = TextWrap.Text(text = item.path)
            )
            val originalSizeBytes = item.size
            val originalSize = bytesToHumanReadableUseCase.invoke(originalSizeBytes)
            addItem(
                icon = IconWrap(imageVector = Icons.Outlined.SdCard),
                title = TextWrap.Resource(id = R.string.originalSize),
                summary = TextWrap.Text(text = "$originalSize ($originalSizeBytes B)")
            )
            if (item.preview != null) {
                val thumbnailFile = filesUtil.getLocalFile(item.preview)
                val thumbnailSizeBytes = thumbnailFile.length()
                val thumbnailSize = bytesToHumanReadableUseCase.invoke(thumbnailSizeBytes)
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.FolderOpen),
                    title = TextWrap.Resource(id = R.string.thumbnailPath),
                    summary = TextWrap.Text(text = item.preview!!)
                )
                addItem(
                    icon = IconWrap(imageVector = Icons.Outlined.SdCard),
                    title = TextWrap.Resource(id = R.string.thumbnailSize),
                    summary = TextWrap.Text(text = "$thumbnailSize ($thumbnailSizeBytes B)")
                )
            }
        }
    }

    private fun MutableList<DetailsItem>.addImageFlags(flags: FileFlags.Image) {
        addItem(
            icon = IconWrap(imageVector = Icons.Outlined.PhotoSizeSelectLarge),
            title = TextWrap.Resource(id = R.string.imageResolution),
            summary = TextWrap.Text(text = flags.resolution)
        )
    }

    private fun MutableList<DetailsItem>.addMusicFlags(flags: FileFlags.Music) {
        addItem(
            icon = IconWrap(imageVector = Icons.Outlined.Headphones),
            title = TextWrap.Resource(id = R.string.sample_rate),
            summary = TextWrap.Text(text = "${flags.sampleRate}kHz")
        )
    }

    private fun MutableList<DetailsItem>.addVideoFlags(flags: FileFlags.Video) {
        addItem(
            icon = IconWrap(imageVector = Icons.Outlined.PhotoSizeSelectLarge),
            title = TextWrap.Resource(id = R.string.videoResolution),
            summary = TextWrap.Text(text = flags.resolution)
        )
    }*/

}