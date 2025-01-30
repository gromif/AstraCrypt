package com.nevidimka655.astracrypt.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.encoders.Base64Encoder
import javax.inject.Qualifier
import javax.inject.Singleton

private const val AUTH = "auth"

@Module
@InstallIn(SingletonComponent::class)
internal object DatastoreModule {

    @AuthDataStore
    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context,
        keysetManager: KeysetManager,
        base64Encoder: Base64Encoder
    ):  DataStore<Preferences> = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        produceFile = { context.preferencesDataStoreFile(AUTH) }
    )

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthDataStore