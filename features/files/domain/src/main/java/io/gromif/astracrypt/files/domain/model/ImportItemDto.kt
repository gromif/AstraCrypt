package io.gromif.astracrypt.files.domain.model

data class ImportItemDto(
    val parent: Long,
    val name: String,
    val itemState: ItemState,
    val itemType: ItemType,
    val file: String?,
    val preview: String?,
    val flags: String?,
    val creationTime: Long,
    val size: Long,
)
