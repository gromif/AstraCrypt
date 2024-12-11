@file:Suppress("ObjectPropertyName")

package com.nevidimka655.astracrypt.app.theme.icons

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