package io.gromif.astracrypt.files.data.db.converters

import androidx.room.TypeConverter
import io.gromif.astracrypt.files.domain.model.ItemType

class ItemTypeConverter {
    @TypeConverter
    fun fromItemType(value: ItemType): Int = value.ordinal

    @TypeConverter
    fun toItemType(value: Int): ItemType = ItemType.entries[value]
}
