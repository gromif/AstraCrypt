@file:Suppress("ClassName")

package io.gromif.astracrypt.files.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.nevidimka655.astracrypt.resources.R
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.shared.icons.Audio
import io.gromif.astracrypt.files.shared.icons.AudioAlt
import io.gromif.astracrypt.files.shared.icons.File
import io.gromif.astracrypt.files.shared.icons.FileAlt
import io.gromif.astracrypt.files.shared.icons.Photo
import io.gromif.astracrypt.files.shared.icons.PhotoAlt
import io.gromif.astracrypt.files.shared.icons.Video
import io.gromif.astracrypt.files.shared.icons.VideoAlt

internal val Icons.FileType get() = _FileType

internal object _FileType

internal val _FileType.Folder @Composable get() = Icons.Default.Folder

internal val FileType.icon @Composable get() = when (this) {
    FileType.Folder -> Icons.FileType.Folder
    FileType.Photo -> Icons.FileType.Photo
    FileType.Music -> Icons.FileType.Audio
    FileType.Video -> Icons.FileType.Video

    FileType.Document,
    FileType.Application,
    FileType.Other,
    FileType.Text,
        -> Icons.FileType.File
}

internal val FileType.iconAlt @Composable get() = when (this) {
    FileType.Folder -> Icons.FileType.Folder
    FileType.Photo -> Icons.FileType.PhotoAlt
    FileType.Music -> Icons.FileType.AudioAlt
    FileType.Video -> Icons.FileType.VideoAlt

    FileType.Document,
    FileType.Application,
    FileType.Other,
    FileType.Text,
        -> Icons.FileType.FileAlt
}

internal val FileType.title get() = when (this) {
    FileType.Folder -> R.string.folder
    FileType.Photo -> R.string.photo
    FileType.Music -> R.string.music
    FileType.Video -> R.string.video
    FileType.Document -> R.string.document
    FileType.Application -> R.string.application
    FileType.Other -> R.string.other
    FileType.Text -> R.string.text
}

internal val FileType.iconTint @Composable get() = when (this) {
    FileType.Folder -> MaterialTheme.colorScheme.onSurfaceVariant
    else -> Color.Unspecified
}