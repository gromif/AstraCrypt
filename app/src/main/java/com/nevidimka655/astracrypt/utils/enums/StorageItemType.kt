package com.nevidimka655.astracrypt.utils.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.icons.*

enum class StorageItemType(
    @StringRes
    val title: Int,
    @DrawableRes
    val iconView: Int,
    @DrawableRes
    val iconAltView: Int
) {
    Folder(
        title = R.string.folder,
        iconView = R.drawable.ic_menu_files,
        iconAltView = R.drawable.ic_menu_files
    ),
    Photo(
        title = R.string.photo,
        iconView = R.drawable.ic_photo,
        iconAltView = R.drawable.ic_photo_variant
    ),
    Music(
        title = R.string.music,
        iconView = R.drawable.ic_audio,
        iconAltView = R.drawable.ic_audio_variant
    ),
    Video(
        title = R.string.video,
        iconView = R.drawable.ic_video,
        iconAltView = R.drawable.ic_video_variant
    ),
    Document(
        title = R.string.document,
        iconView = R.drawable.ic_file,
        iconAltView = R.drawable.ic_file_variant
    ),
    Application(
        title = R.string.application,
        iconView = R.drawable.ic_file,
        iconAltView = R.drawable.ic_file_variant
    ),
    Other(
        title = R.string.other,
        iconView = R.drawable.ic_file,
        iconAltView = R.drawable.ic_file_variant
    ),
    Text(
        title = R.string.text,
        iconView = R.drawable.ic_file,
        iconAltView = R.drawable.ic_file_variant
    );

    val isFile get() = this != Folder

    val icon @Composable get() = when(this) {
        Folder -> Icons.FileType.Folder
        Photo -> Icons.FileType.Photo
        Music -> Icons.FileType.Audio
        Video -> Icons.FileType.Video
        else -> Icons.FileType.File
    }

    val iconAlt @Composable get() = when(this) {
        Folder -> Icons.FileType.Folder
        Photo -> Icons.FileType.PhotoAlt
        Music -> Icons.FileType.AudioAlt
        Video -> Icons.FileType.VideoAlt
        else -> Icons.FileType.FileAlt
    }

}