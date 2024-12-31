package com.nevidimka655.astracrypt.data.files.db.tuples

import androidx.room.ColumnInfo

data class OpenTuple(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "path") val path: String
)