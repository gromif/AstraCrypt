package com.nevidimka655.astracrypt.data.database.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.data.database.StorageItemType

class StorageItemTypeConverter {
    @TypeConverter
    fun fromInt(value: Int) = StorageItemType.entries[value]

    @TypeConverter
    fun toInt(value: StorageItemType) = value.ordinal
}