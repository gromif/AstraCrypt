package io.gromif.astracrypt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nevidimka655.notes.data.db.NoteItemEntity
import com.nevidimka655.notes.data.db.NotesDao
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.converters.ItemStateConverter
import io.gromif.astracrypt.files.data.db.converters.ItemTypeConverter

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