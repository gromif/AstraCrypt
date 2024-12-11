package com.nevidimka655.astracrypt.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nevidimka655.astracrypt.domain.room.daos.NotesDao
import com.nevidimka655.astracrypt.domain.room.daos.StorageItemDao
import com.nevidimka655.astracrypt.domain.room.entities.NoteItemEntity
import com.nevidimka655.astracrypt.domain.room.entities.StorageItemEntity

@TypeConverters(
    com.nevidimka655.astracrypt.data.room.converts.StorageItemTypeConverter::class,
    com.nevidimka655.astracrypt.data.room.converts.StorageItemStateConverter::class
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