package io.gromif.astracrypt.files.data.db.tuples

import androidx.room.ColumnInfo

data class OpenTuple(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "file") val path: String
)