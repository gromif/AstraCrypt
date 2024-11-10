@file:Suppress("ObjectPropertyName")

package com.nevidimka655.astracrypt.ui.theme.icons

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.ext.vectorResource

val Icons.FileType get() = _FileType
object _FileType

val _FileType.Folder
    @Composable get() = _folder
        ?: vectorResource(id = R.drawable.ic_menu_files).also { _folder = it }
private var _folder: ImageVector? = null

val _FileType.Audio
    @Composable get() = _audio
        ?: vectorResource(id = R.drawable.ic_audio).also { _audio = it }
private var _audio: ImageVector? = null

val _FileType.Video
    @Composable get() = _audio
        ?: vectorResource(id = R.drawable.ic_video).also { _video = it }
private var _video: ImageVector? = null

val _FileType.Photo
    @Composable get() = _photo
        ?: vectorResource(id = R.drawable.ic_photo).also { _photo = it }
private var _photo: ImageVector? = null

val _FileType.File
    @Composable get() = _file
        ?: vectorResource(id = R.drawable.ic_file).also { _file = it }
private var _file: ImageVector? = null

fun _FileType.reset() {
    _folder = null
    _file = null
    _audio = null
    _photo = null
}