package com.nevidimka655.astracrypt.domain.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nevidimka655.astracrypt.data.room.StorageItemState
import com.nevidimka655.astracrypt.data.room.StorageItemType

@Entity(tableName = "store_items")
data class StorageItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "item_type", typeAffinity = ColumnInfo.INTEGER)
    val itemType: StorageItemType = StorageItemType.Other,
    @ColumnInfo(name = "dir_id") val parentDirectoryId: Long = 0,
    @ColumnInfo(name = "size") val size: Long = 0,
    @ColumnInfo(name = "state") val state: StorageItemState = StorageItemState.Default,
    @ColumnInfo(name = "preview") val preview: String? = null,
    @ColumnInfo(name = "path") val path: String = "",
    @ColumnInfo(name = "flags") val flags: String = "",
    @ColumnInfo(name = "time") val creationTime: Long = 0
)