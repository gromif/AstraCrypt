package com.nevidimka655.astracrypt.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store_items")
data class StorageItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "item_type") val type: Int = 0,
    @ColumnInfo(name = "dir_id") val dirId: Long = 0,
    @ColumnInfo(name = "size") val size: Long = 0,
    @ColumnInfo(name = "state") val state: Int = 0,
    @ColumnInfo(name = "preview") val preview: String? = null,
    @ColumnInfo(name = "path") val path: String = "",
    @ColumnInfo(name = "flags") val flags: String = "",
    @ColumnInfo(name = "time") val creationTime: Long = 0
)