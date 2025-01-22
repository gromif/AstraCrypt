package com.nevidimka655.astracrypt.app.di.crypto

import android.content.Context
import com.nevidimka655.astracrypt.data.crypto.KeysetFactoryImpl
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import com.nevidimka655.astracrypt.domain.usecase.crypto.MasterKeyNameUseCase
import com.nevidimka655.astracrypt.domain.usecase.crypto.PrefsKeyNameUseCase
import com.nevidimka655.crypto.tink.core.GetGlobalAssociatedDataPrf
import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.crypto.tink.core.hash.Sha256Util
import com.nevidimka655.crypto.tink.core.hash.Sha384Util
import com.nevidimka655.crypto.tink.core.parsers.KeysetParserWithAead
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializerWithAead
import com.nevidimka655.crypto.tink.data.AssociatedDataManager
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeysetManagerModule {

    @Singleton
    @Provides
    fun provideKeysetManager(
        keysetFactoryImpl: KeysetFactoryImpl,
        associatedDataManager: AssociatedDataManager
    ) = KeysetManager(
        keysetFactory = keysetFactoryImpl,
        associatedDataManager = associatedDataManager
    )

    @Singleton
    @Provides
    fun provideAssociatedDataManager(
        @ApplicationContext context: Context
    ): AssociatedDataManager = AssociatedDataManager(
        associatedDataFile = File("${context.filesDir}/grapefruit.ss0")
    )

    @Singleton
    @Provides
    fun provideGetGlobalAssociatedDataPrf(
        keysetManager: KeysetManager
    ): GetGlobalAssociatedDataPrf = GetGlobalAssociatedDataPrf(
        keysetManager = keysetManager
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