package com.nevidimka655.astracrypt.room.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.utils.enums.StorageItemState

class StorageItemStateConverter {
    @TypeConverter
    fun fromInt(value: Int) = StorageItemState.entries[value]

    @TypeConverter
    fun toInt(value: StorageItemState) = value.ordinal
}