package io.gromif.astracrypt.files.data.db.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class PagerTuple(
    @PrimaryKey val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: Int,

    @ColumnInfo(name = "preview")
    val preview: String?,

    @ColumnInfo(name = "preview-aead")
    val previewAead: Int,

    @ColumnInfo(name = "state")
    val state: Int
)