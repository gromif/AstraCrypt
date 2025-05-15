package io.gromif.astracrypt.files.domain.model

sealed class ItemDetails(
    val itemName: String
) {

    data class File(
        val name: String,
        val type: ItemType,
        val file: FileSource,
        val preview: FileSource?,
        val flags: FileFlags?,
        val size: Long,
        val creationTime: Long,
    ) : ItemDetails(itemName = name)

    data class Folder(
        val name: String,
        val filesCount: Int,
        val foldersCount: Int,
        val creationTime: Long,
    ) : ItemDetails(itemName = name)
}
