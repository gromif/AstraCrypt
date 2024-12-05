package com.nevidimka655.astracrypt.utils.enums

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.FileType
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.Folder
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.audio.Audio
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.audio.AudioAlt
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.file.File
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.file.FileAlt
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.photo.Photo
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.photo.PhotoAlt
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.video.Video
import com.nevidimka655.astracrypt.ui.theme.icons.file_type.video.VideoAlt

enum class StorageItemType(@StringRes val title: Int) {
    Folder(title = R.string.folder),
    Photo(title = R.string.photo),
    Music(title = R.string.music),
    Video(title = R.string.video),
    Document(title = R.string.document),
    Application(title = R.string.application),
    Other(title = R.string.other),
    Text(title = R.string.text);

    val isFile get() = this != Folder

    val icon
        @Composable get() = when (this) {
            Folder -> Icons.FileType.Folder
            Photo -> Icons.FileType.Photo
            Music -> Icons.FileType.Audio
            Video -> Icons.FileType.Video
            else -> Icons.FileType.File
        }

    val iconAlt
        @Composable get() = when (this) {
            Folder -> Icons.FileType.Folder
            Photo -> Icons.FileType.PhotoAlt
            Music -> Icons.FileType.AudioAlt
            Video -> Icons.FileType.VideoAlt
            else -> Icons.FileType.FileAlt
        }

    val iconTint
        @Composable get() = when (this) {
            Folder -> MaterialTheme.colorScheme.onSurfaceVariant
            else -> Color.Unspecified
        }

}