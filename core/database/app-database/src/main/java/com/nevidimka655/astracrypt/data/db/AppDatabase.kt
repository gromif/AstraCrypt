package com.nevidimka655.astracrypt.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import com.nevidimka655.astracrypt.notes.db.NoteItemEntity
import com.nevidimka655.astracrypt.notes.db.NotesDao

@Database(
    entities = [
        FilesEntity::class,
        NoteItemEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFilesDao(): FilesDao
    abstract fun getNotesDao(): NotesDao
}