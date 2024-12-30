package com.nevidimka655.astracrypt.app.di.crypto

import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.astracrypt.data.crypto.KeysetFactoryImpl
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import com.nevidimka655.astracrypt.utils.io.FilesUtil
import com.nevidimka655.astracrypt.domain.usecase.crypto.MasterKeyNameUseCase
import com.nevidimka655.astracrypt.domain.usecase.crypto.PrefsKeyNameUseCase
import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.crypto.tink.core.hash.Sha256Util
import com.nevidimka655.crypto.tink.core.hash.Sha384Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.core.parsers.KeysetParserWithAead
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializerWithAead
import com.nevidimka655.crypto.tink.domain.AssociatedDataConfig
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
        filesUtil: FilesUtil,
        keysetFactoryImpl: KeysetFactoryImpl
    ) = KeysetManager(
        associatedDataConfig = AssociatedDataConfig(
            dataFile = File("${filesUtil.dataDir}/grapefruit.ss0"),
            dataLength = AppConfig.CRYPTO_NONCE_SIZE,
            dataPasswordHashLength = AppConfig.AUTH_PASSWORD_HASH_LENGTH
        ),
        keysetFactory = keysetFactoryImpl
    )


    @Singleton
    @Provides
    fun provideKeysetFactory(
        keysetDataStoreManager: KeysetDataStoreManager,
        keysetSerializerWithAead: KeysetSerializerWithAead,
        keysetParserWithAead: KeysetParserWithAead,
        prefsKeyNameUseCase: PrefsKeyNameUseCase,
        masterKeyNameUseCase: MasterKeyNameUseCase
    ) = KeysetFactoryImpl(
        keysetDataStoreManager = keysetDataStoreManager,
        keysetSerializerWithAead = keysetSerializerWithAead,
        keysetParserWithAead = keysetParserWithAead,
        prefsKeyNameUseCase = prefsKeyNameUseCase,
        masterKeyNameUseCase = masterKeyNameUseCase
    )

    @Singleton
    @Provides
    fun provideMasterKeyNameUseCase(
        hexUtil: HexUtil,
        sha256Util: Sha256Util
    ) = MasterKeyNameUseCase(hexUtil = hexUtil, sha256Util = sha256Util)

    @Singleton
    @Provides
    fun providePrefsKeyNameUseCase(
        hexUtil: HexUtil,
        sha384Util: Sha384Util
    ) = PrefsKeyNameUseCase(hexUtil = hexUtil, sha384Util = sha384Util)

}