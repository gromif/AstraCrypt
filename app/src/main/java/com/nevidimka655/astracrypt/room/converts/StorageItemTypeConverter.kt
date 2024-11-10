package com.nevidimka655.astracrypt.room.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.utils.enums.StorageItemType

class StorageItemTypeConverter {
    @TypeConverter
    fun fromInt(value: Int) = StorageItemType.entries[value]

    @TypeConverter
    fun toInt(value: StorageItemType) = value.ordinal
}