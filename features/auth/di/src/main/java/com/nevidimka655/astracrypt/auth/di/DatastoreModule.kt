package com.nevidimka655.astracrypt.auth.di

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.nevidimka655.astracrypt.auth.data.datastore.AuthDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.core.encoders.Base64Util
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.tink_datastore.TinkDataStore
import javax.inject.Singleton

private const val AUTH = "auth"

@Module
@InstallIn(SingletonComponent::class)
internal object DatastoreModule {

    @Singleton
    @Provides
    fun provideAuthDataStoreManager(
        @ApplicationContext context: Context,
        keysetManager: KeysetManager,
        base64Util: Base64Util
    ): AuthDataStoreManager = AuthDataStoreManager(
        dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile(AUTH) }
        ),
        keysetManager = keysetManager,
        base64Util = base64Util,
        tinkDataStoreParams = TinkDataStore.Params(purpose = "auth")
    )

}