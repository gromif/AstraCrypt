package com.nevidimka655.astracrypt.data.database

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nevidimka655.atracrypt.core.design_system.icons.Audio
import com.nevidimka655.atracrypt.core.design_system.icons.AudioAlt
import com.nevidimka655.atracrypt.core.design_system.icons.File
import com.nevidimka655.atracrypt.core.design_system.icons.FileAlt
import com.nevidimka655.atracrypt.core.design_system.icons.FileType
import com.nevidimka655.atracrypt.core.design_system.icons.Folder
import com.nevidimka655.atracrypt.core.design_system.icons.Photo
import com.nevidimka655.atracrypt.core.design_system.icons.PhotoAlt
import com.nevidimka655.atracrypt.core.design_system.icons.Video
import com.nevidimka655.atracrypt.core.design_system.icons.VideoAlt
import com.nevidimka655.astracrypt.resources.R

enum class FileTypes(@StringRes val title: Int) {
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