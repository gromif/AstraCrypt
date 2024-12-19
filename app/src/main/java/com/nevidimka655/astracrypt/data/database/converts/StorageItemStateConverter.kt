package com.nevidimka655.astracrypt.data.database.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.data.database.StorageItemState

class StorageItemStateConverter {
    @TypeConverter
    fun fromInt(value: Int) = StorageItemState.entries[value]

    @TypeConverter
    fun toInt(value: StorageItemState) = value.ordinal
}