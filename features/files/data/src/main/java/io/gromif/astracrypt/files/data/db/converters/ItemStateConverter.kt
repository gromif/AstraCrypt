package io.gromif.astracrypt.files.data.db.converters

import androidx.room.TypeConverter
import io.gromif.astracrypt.files.domain.model.ItemState

class ItemStateConverter {
    @TypeConverter
    fun fromItemState(value: ItemState): Int = value.ordinal

    @TypeConverter
    fun toItemState(value: Int): ItemState = ItemState.entries[value]
}