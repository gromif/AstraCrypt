package io.gromif.astracrypt.files.data.db

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
    val parent: Long = 0,
    val name: String = "",
    val state: ItemState = ItemState.Default,
    val type: ItemType = ItemType.Folder,
    val file: String? = null,
    val fileAead: Int = -1,
    val preview: String? = null,
    val previewAead: Int = -1,
    val flags: String? = null,
    val time: Long = 0,
    val size: Long = 0,
)
