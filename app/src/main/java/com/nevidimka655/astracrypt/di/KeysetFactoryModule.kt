package com.nevidimka655.astracrypt.di

import android.content.Context
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsKeys
import com.nevidimka655.crypto.tink.KeysetFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

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
            prefsFileNameDefault = PrefsKeys.FileNames.MASTER,
            prefs = context.getSharedPreferences(
                PrefsKeys.FileNames.MASTER, Context.MODE_PRIVATE
            ),
            prefsUniqueSaltFieldKey = PrefsKeys.ENCRYPTION_UNIQUE_SALT
        )
    )

}