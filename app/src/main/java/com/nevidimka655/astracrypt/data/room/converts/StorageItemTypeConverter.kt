package com.nevidimka655.astracrypt.data.room.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.data.room.StorageItemType

class StorageItemTypeConverter {
    @TypeConverter
    fun fromInt(value: Int) = StorageItemType.entries[value]

    @TypeConverter
    fun toInt(value: StorageItemType) = value.ordinal
}