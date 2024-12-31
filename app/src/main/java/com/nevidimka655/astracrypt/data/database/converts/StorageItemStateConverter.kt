package com.nevidimka655.astracrypt.data.database.converts

import androidx.room.TypeConverter
import com.nevidimka655.astracrypt.domain.model.db.FileState

class StorageItemStateConverter {
    @TypeConverter
    fun fromInt(value: Int) = FileState.entries[value]

    @TypeConverter
    fun toInt(value: FileState) = value.ordinal
}