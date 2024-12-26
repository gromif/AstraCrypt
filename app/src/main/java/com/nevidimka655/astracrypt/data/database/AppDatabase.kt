package com.nevidimka655.astracrypt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nevidimka655.astracrypt.data.database.daos.StorageItemDao
import com.nevidimka655.astracrypt.data.database.entities.StorageItemEntity
import com.nevidimka655.astracrypt.notes.db.NoteItemEntity
import com.nevidimka655.astracrypt.notes.db.NotesDao

@TypeConverters(
    com.nevidimka655.astracrypt.data.database.converts.StorageItemTypeConverter::class,
    com.nevidimka655.astracrypt.data.database.converts.StorageItemStateConverter::class
)
@Database(
    entities = [
        StorageItemEntity::class,
        NoteItemEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStorageItemDao(): StorageItemDao
    abstract fun getNotesDao(): NotesDao
}