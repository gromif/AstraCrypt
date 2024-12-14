package com.nevidimka655.astracrypt.app.di

import android.content.Context
import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.crypto.tink.KeysetFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

private const val KEYSET_PREFERENCES_NAME = "banana"
private const val ENCRYPTION_UNIQUE_SALT = "apricot"

@Module
@InstallIn(SingletonComponent::class)
object KeysetFactoryModule {

    @Singleton
    @Provides
    fun provideKeysetFactory(
        @ApplicationContext context: Context,
        io: Io
    ) = KeysetFactory(
        context = context,
        associatedDataConfig = KeysetFactory.AssociatedDataConfig(
            dataFile = File("${io.dataDir}/grapefruit.ss0"),
            dataLength = AppConfig.CRYPTO_NONCE_SIZE,
            dataPasswordHashLength = AppConfig.AUTH_PASSWORD_HASH_LENGTH
        ),
        prefsConfig = KeysetFactory.PrefsConfig(
            prefsFileNameDefault = KEYSET_PREFERENCES_NAME,
            prefs = context.getSharedPreferences(
                KEYSET_PREFERENCES_NAME, Context.MODE_PRIVATE
            ),
            prefsUniqueSaltFieldKey = ENCRYPTION_UNIQUE_SALT
        )
    )

}