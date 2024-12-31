package com.nevidimka655.astracrypt.domain.model.db

import com.nevidimka655.astracrypt.data.database.FileTypes

data class FileItem(
    val id: Long = 0,
    val parent: Long = 0,
    val name: String = "",
    val preview: SerializedFile? = null,
    val file: SerializedFile? = null,
    val size: Long = 0,
    val type: FileTypes = FileTypes.Other,
    val isFolder: Boolean = type == FileTypes.Folder,
    val isFile: Boolean = !isFolder,
    val state: FileState = FileState.Default,
    val time: FileTime? = null
)