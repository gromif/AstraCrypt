@file:Suppress("ObjectPropertyName")

package com.nevidimka655.astracrypt.ui.theme.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.ext.vectorResource

val _FileType.AudioAlt
    @Composable get() = _audioAlt
        ?: vectorResource(id = R.drawable.ic_audio_variant).also { _audioAlt = it }
private var _audioAlt: ImageVector? = null

val _FileType.VideoAlt
    @Composable get() = _videoAlt
        ?: vectorResource(id = R.drawable.ic_video_variant).also { _videoAlt = it }
private var _videoAlt: ImageVector? = null

val _FileType.PhotoAlt
    @Composable get() = _photoAlt
        ?: vectorResource(id = R.drawable.ic_photo_variant).also { _photoAlt = it }
private var _photoAlt: ImageVector? = null

val _FileType.FileAlt
    @Composable get() = _fileAlt
        ?: vectorResource(id = R.drawable.ic_file_variant).also { _fileAlt = it }
private var _fileAlt: ImageVector? = null

fun _FileType.resetAlt() {
    _audioAlt = null
    _photoAlt = null
    _fileAlt = null
}