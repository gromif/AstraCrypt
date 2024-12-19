package com.nevidimka655.astracrypt.app.di

import com.google.crypto.tink.config.TinkConfig
import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.data.crypto.KeysetFactoryImpl
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import com.nevidimka655.astracrypt.domain.usecase.crypto.MasterKeyNameUseCase
import com.nevidimka655.astracrypt.domain.usecase.crypto.PrefsKeyNameUseCase
import com.nevidimka655.crypto.tink.KeysetManager
import com.nevidimka655.crypto.tink.domain.model.AssociatedDataConfig
import com.nevidimka655.crypto.tink.domain.usecase.ParseKeysetByAeadUseCase
import com.nevidimka655.crypto.tink.domain.usecase.SerializeKeysetByAeadUseCase
import com.nevidimka655.crypto.tink.domain.usecase.encoder.HexUseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha256UseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha384UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeysetManagerModule {

    @Singleton
    @Provides
    fun provideKeysetManager(
        io: Io,
        keysetFactoryImpl: KeysetFactoryImpl
    ) = KeysetManager(
        associatedDataConfig = AssociatedDataConfig(
            dataFile = File("${io.dataDir}/grapefruit.ss0"),
            dataLength = AppConfig.CRYPTO_NONCE_SIZE,
            dataPasswordHashLength = AppConfig.AUTH_PASSWORD_HASH_LENGTH
        ),
        keysetFactory = keysetFactoryImpl
    ).also {
        TinkConfig.register() // Register all Tink types
    }


    @Singleton
    @Provides
    fun provideKeysetFactory(
        keysetDataStoreManager: KeysetDataStoreManager,
        serializeKeysetByAeadUseCase: SerializeKeysetByAeadUseCase,
        parseKeysetByAeadUseCase: ParseKeysetByAeadUseCase,
        prefsKeyNameUseCase: PrefsKeyNameUseCase,
        masterKeyNameUseCase: MasterKeyNameUseCase
    ) = KeysetFactoryImpl(
        keysetDataStoreManager = keysetDataStoreManager,
        serializeKeysetByAeadUseCase = serializeKeysetByAeadUseCase,
        parseKeysetByAeadUseCase = parseKeysetByAeadUseCase,
        prefsKeyNameUseCase = prefsKeyNameUseCase,
        masterKeyNameUseCase = masterKeyNameUseCase
    )

    @Singleton
    @Provides
    fun provideMasterKeyNameUseCase(
        hexUseCase: HexUseCase,
        sha256UseCase: Sha256UseCase
    ) = MasterKeyNameUseCase(hexUseCase = hexUseCase, sha256UseCase = sha256UseCase)

    @Singleton
    @Provides
    fun providePrefsKeyNameUseCase(
        hexUseCase: HexUseCase,
        sha384UseCase: Sha384UseCase
    ) = PrefsKeyNameUseCase(hexUseCase = hexUseCase, sha384UseCase = sha384UseCase)

}