package com.nevidimka655.astracrypt.data.files.db.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class ExportTuple(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String
)