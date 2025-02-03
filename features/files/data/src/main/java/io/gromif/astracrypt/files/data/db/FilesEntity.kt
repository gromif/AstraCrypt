package io.gromif.astracrypt.files.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType

@Entity(
    tableName = "store_items",
    indices = [
        Index(value = ["parent", "state"]),
        Index(value = ["type"]),
    ]
)
data class FilesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "parent")
    val parent: Long = 0,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "state")
    val state: ItemState = ItemState.Default,

    @ColumnInfo(name = "type")
    val type: ItemType = ItemType.Folder,

    @ColumnInfo(name = "file")
    val file: String? = null,
    @ColumnInfo(name = "fileAead")
    val fileAead: Int = -1,

    @ColumnInfo(name = "preview")
    val preview: String? = null,
    @ColumnInfo(name = "previewAead")
    val previewAead: Int = -1,

    @ColumnInfo(name = "flags")
    val flags: String? = null,

    @ColumnInfo(name = "time")
    val time: Long = 0,

    @ColumnInfo(name = "size")
    val size: Long = 0,
)