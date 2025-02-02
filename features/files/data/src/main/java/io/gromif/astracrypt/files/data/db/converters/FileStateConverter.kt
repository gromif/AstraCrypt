package io.gromif.astracrypt.files.data.db.converters

import androidx.room.TypeConverter
import io.gromif.astracrypt.files.domain.model.ItemState

class FileStateConverter {
    @TypeConverter
    fun fromFileState(value: ItemState): Int = value.ordinal

    @TypeConverter
    fun toFileState(value: Int): ItemState = ItemState.entries[value]
}