package io.gromif.astracrypt.files.data.db.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class MinimalTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "file") val file: String?,
    @ColumnInfo(name = "preview") val preview: String?
)