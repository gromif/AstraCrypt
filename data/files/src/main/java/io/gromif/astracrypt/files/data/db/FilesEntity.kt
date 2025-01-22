package io.gromif.astracrypt.files.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store_items")
data class FilesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "parent")
    val parent: Long = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "state")
    val state: Int = 0,

    @ColumnInfo(name = "type")
    val type: Int = 0,

    @ColumnInfo(name = "file")
    val file: String? = null,
    @ColumnInfo(name = "file-aead")
    val fileAead: Int = -1,

    @ColumnInfo(name = "preview")
    val preview: String? = null,
    @ColumnInfo(name = "preview-aead")
    val previewAead: Int = -1,

    @ColumnInfo(name = "flags")
    val flags: String? = null,

    @ColumnInfo(name = "time")
    val creationTime: Long = 0,

    @ColumnInfo(name = "size")
    val size: Long = 0,
)