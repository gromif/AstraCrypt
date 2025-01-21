package io.gromif.astracrypt.files.data.db.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class FilesDirMinimalTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "parent") val parentDirectoryId: Long
)