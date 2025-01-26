package io.gromif.astracrypt.files.data.db.converters

import androidx.room.TypeConverter
import io.gromif.astracrypt.files.domain.model.FileType

class FileTypeConverter {
    @TypeConverter
    fun fromFileType(value: FileType): Int = value.ordinal

    @TypeConverter
    fun toFileType(value: Int): FileType = FileType.entries[value]
}