package com.nevidimka655.astracrypt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nevidimka655.astracrypt.data.files.db.FilesDao
import com.nevidimka655.astracrypt.data.files.db.FilesEntity
import com.nevidimka655.astracrypt.notes.db.NoteItemEntity
import com.nevidimka655.astracrypt.notes.db.NotesDao

@TypeConverters(
    com.nevidimka655.astracrypt.data.database.converts.StorageItemTypeConverter::class,
    com.nevidimka655.astracrypt.data.database.converts.StorageItemStateConverter::class
)
@Database(
    entities = [
        FilesEntity::class,
        NoteItemEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStorageItemDao(): FilesDao
    abstract fun getNotesDao(): NotesDao
}