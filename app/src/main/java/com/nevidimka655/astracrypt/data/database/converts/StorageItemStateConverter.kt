package com.nevidimka655.astracrypt.data.database.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.domain.model.db.StorageState

class StorageItemStateConverter {
    @TypeConverter
    fun fromInt(value: Int) = StorageState.entries[value]

    @TypeConverter
    fun toInt(value: StorageState) = value.ordinal
}