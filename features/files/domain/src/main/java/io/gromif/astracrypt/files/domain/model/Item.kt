package io.gromif.astracrypt.files.domain.model

data class Item(
    val id: Long = 0,
    val parent: Long = 0,
    val name: String = "",
    val preview: FileSource? = null,
    val file: FileSource? = null,
    val size: Long = 0,
    val type: ItemType = ItemType.Other,
    val isFolder: Boolean = type == ItemType.Folder,
    val isFile: Boolean = !isFolder,
    val state: FileState = FileState.Default
)