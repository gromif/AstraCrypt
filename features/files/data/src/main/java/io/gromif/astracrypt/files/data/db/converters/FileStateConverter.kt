package io.gromif.astracrypt.files.data.db.converters

import androidx.room.TypeConverter
import io.gromif.astracrypt.files.domain.model.FileState

class FileStateConverter {
    @TypeConverter
    fun fromFileState(value: FileState): Int = value.ordinal

    @TypeConverter
    fun toFileState(value: Int): FileState = FileState.entries[value]
}