@file:Suppress("ClassName")

package io.gromif.astracrypt.files.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.shared.icons.Audio
import io.gromif.astracrypt.files.shared.icons.AudioAlt
import io.gromif.astracrypt.files.shared.icons.File
import io.gromif.astracrypt.files.shared.icons.FileAlt
import io.gromif.astracrypt.files.shared.icons.Photo
import io.gromif.astracrypt.files.shared.icons.PhotoAlt
import io.gromif.astracrypt.files.shared.icons.Video
import io.gromif.astracrypt.files.shared.icons.VideoAlt
import io.gromif.astracrypt.resources.R

val Icons.FileType get() = _FileType

object _FileType

val _FileType.Folder @Composable get() = Icons.Default.Folder

val ItemType.icon @Composable get() = when (this) {
    ItemType.Folder -> Icons.FileType.Folder
    ItemType.Photo -> Icons.FileType.Photo
    ItemType.Music -> Icons.FileType.Audio
    ItemType.Video -> Icons.FileType.Video

    ItemType.Document,
    ItemType.Application,
    ItemType.Other,
    ItemType.Text,
    -> Icons.FileType.File
}

val ItemType.iconAlt @Composable get() = when (this) {
    ItemType.Folder -> Icons.FileType.Folder
    ItemType.Photo -> Icons.FileType.PhotoAlt
    ItemType.Music -> Icons.FileType.AudioAlt
    ItemType.Video -> Icons.FileType.VideoAlt

    ItemType.Document,
    ItemType.Application,
    ItemType.Other,
    ItemType.Text,
    -> Icons.FileType.FileAlt
}

val ItemType.title get() = when (this) {
    ItemType.Folder -> R.string.folder
    ItemType.Photo -> R.string.photo
    ItemType.Music -> R.string.music
    ItemType.Video -> R.string.video
    ItemType.Document -> R.string.document
    ItemType.Application -> R.string.application
    ItemType.Other -> R.string.other
    ItemType.Text -> R.string.text
}

val ItemType.iconTint @Composable get() = when (this) {
    ItemType.Folder -> MaterialTheme.colorScheme.onSurfaceVariant
    else -> Color.Unspecified
}
