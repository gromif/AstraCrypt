package com.nevidimka655.astracrypt.app.di.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

private const val DEFAULT = "pomegranate"
private const val SETTINGS = "tomato"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @DefaultDataStore
    @Singleton
    @Provides
    fun provideDefaultDataStore(
        @ApplicationContext context: Context
    ) = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        produceFile = { context.preferencesDataStoreFile(DEFAULT) }
    )

    @SettingsDataStore
    @Singleton
    @Provides
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ) = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        produceFile = { context.preferencesDataStoreFile(SETTINGS) }
    )

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SettingsDataStore