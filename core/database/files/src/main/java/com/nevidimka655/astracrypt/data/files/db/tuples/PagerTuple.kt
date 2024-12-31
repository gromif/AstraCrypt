package com.nevidimka655.astracrypt.data.files.db.tuples

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class PagerTuple(
    @PrimaryKey val id: Long = -1,

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "item_type")
    val itemType: Int = 0,

    @ColumnInfo(name = "preview")
    val preview: String? = null,

    @ColumnInfo(name = "state")
    val state: Int = 0
)