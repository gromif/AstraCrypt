@file:Suppress("ObjectPropertyName")

package com.nevidimka655.astracrypt.ui.theme.icons.file_type

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter

val Icons.FileType get() = _FileType
object _FileType {
    object Builder
}

val _FileType.Folder
    @Composable get() = rememberVectorPainter(Icons.Default.Folder)


fun _FileType.reset() {
}