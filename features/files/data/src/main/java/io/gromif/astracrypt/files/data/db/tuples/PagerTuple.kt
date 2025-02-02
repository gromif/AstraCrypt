package io.gromif.astracrypt.files.data.db.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import io.gromif.astracrypt.files.domain.model.FileType

data class PagerTuple(
    @PrimaryKey val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: FileType,

    @ColumnInfo(name = "preview")
    val preview: String?,

    @ColumnInfo(name = "previewAead")
    val previewAead: Int,

    @ColumnInfo(name = "state")
    val state: Int
)