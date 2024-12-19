package com.nevidimka655.astracrypt.domain.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.nevidimka655.astracrypt.data.database.StorageItemState
import com.nevidimka655.astracrypt.data.database.StorageItemType

data class PagerTuple(
    @PrimaryKey val id: Long = -1,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "item_type", typeAffinity = ColumnInfo.INTEGER)
    val itemType: StorageItemType = StorageItemType.Folder,

    @ColumnInfo(name = "preview")
    val preview: String? = null,

    @ColumnInfo(name = "state")
    val state: StorageItemState = StorageItemState.Default
) {
    val isFile get() = itemType.isFile
    val isDirectory get() = !isFile
}

data class StorageItemMinimalTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "preview") val preview: String,
    @ColumnInfo(name = "path") val path: String
)

data class StorageDirMinimalTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "dir_id") val parentDirectoryId: Long
)

data class OpenTuple(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String
)

data class ExportTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String
)

data class DatabaseTransformTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "preview") val preview: String,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "flags") val flags: String
)

// Notes

data class NotesPagerTuple(
    @PrimaryKey val id: Long = 0,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "text_preview") val textPreview: String? = null,
    @ColumnInfo(name = "time_c") val creationTime: Long = 0
)

data class TransformNotesTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "text_preview") val textPreview: String?
)