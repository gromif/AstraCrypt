package com.nevidimka655.astracrypt.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nevidimka655.astracrypt.utils.enums.StorageItemState
import com.nevidimka655.astracrypt.utils.enums.StorageItemType

@Entity(tableName = "store_items")
data class StorageItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "item_type", typeAffinity = ColumnInfo.INTEGER)
    val itemType: StorageItemType = StorageItemType.Other,
    @ColumnInfo(name = "dir_id") val parentDirectoryId: Long = 0,
    @ColumnInfo(name = "size_bytes") val originalSizeInBytes: Long = 0,
    @ColumnInfo(name = "state") val state: StorageItemState = StorageItemState.Default,
    @ColumnInfo(name = "thumb") val thumb: String = "",
    @ColumnInfo(name = "path") val path: String = "",
    @ColumnInfo(name = "enc_type") val encryptionType: Int = -1,
    @ColumnInfo(name = "enc_thumb_type") val thumbnailEncryptionType: Int = -1,
    @ColumnInfo(name = "flags") val flags: String = "",
    @ColumnInfo(name = "time_c") val creationTime: Long = 0,
    @ColumnInfo(name = "time_m") val modificationTime: Long = 0
)