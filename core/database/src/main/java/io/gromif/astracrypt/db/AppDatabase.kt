package io.gromif.astracrypt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.converters.ItemStateConverter
import io.gromif.astracrypt.files.data.db.converters.ItemTypeConverter
import io.gromif.notes.data.db.NoteItemEntity
import io.gromif.notes.data.db.NotesDao

@Database(
    entities = [
        FilesEntity::class,
        NoteItemEntity::class
    ],
    version = 1
)
@TypeConverters(
    ItemStateConverter::class,
    ItemTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFilesDao(): FilesDao
    abstract fun getNotesDao(): NotesDao
}