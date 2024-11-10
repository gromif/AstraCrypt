package com.nevidimka655.astracrypt.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nevidimka655.astracrypt.room.converts.StorageItemStateConverter
import com.nevidimka655.astracrypt.room.converts.StorageItemTypeConverter
import com.nevidimka655.astracrypt.room.daos.NotesDao
import com.nevidimka655.astracrypt.room.daos.StorageItemDao
import com.nevidimka655.astracrypt.room.entities.NoteItemEntity
import com.nevidimka655.astracrypt.room.entities.StorageItemEntity

@TypeConverters(
    StorageItemTypeConverter::class,
    StorageItemStateConverter::class
)
@Database(
    entities = [
        StorageItemEntity::class,
        NoteItemEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStorageItemDao(): StorageItemDao
    abstract fun getNotesDao(): NotesDao
}