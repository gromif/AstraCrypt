@file:Suppress("ClassName")

package com.nevidimka655.atracrypt.core.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable

val Icons.FileType get() = _FileType

object _FileType {
    object Builder
}

val _FileType.Folder
    @Composable get() = Icons.Default.Folder