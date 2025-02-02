package io.gromif.astracrypt.files.data.db.converters

import androidx.room.TypeConverter
import io.gromif.astracrypt.files.domain.model.ItemType

class FileTypeConverter {
    @TypeConverter
    fun fromFileType(value: ItemType): Int = value.ordinal

    @TypeConverter
    fun toFileType(value: Int): ItemType = ItemType.entries[value]
}