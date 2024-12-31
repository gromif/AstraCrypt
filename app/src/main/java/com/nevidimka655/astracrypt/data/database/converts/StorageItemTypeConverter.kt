package com.nevidimka655.astracrypt.data.database.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.data.database.FileTypes

class StorageItemTypeConverter {
    @TypeConverter
    fun fromInt(value: Int) = FileTypes.entries[value]

    @TypeConverter
    fun toInt(value: FileTypes) = value.ordinal
}