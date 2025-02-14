package com.nevidimka655.astracrypt.app.di.crypto

import android.content.Context
import com.nevidimka655.astracrypt.data.crypto.KeysetFactoryImpl
import com.nevidimka655.astracrypt.data.datastore.KeysetDataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.core.GetGlobalAssociatedDataPrf
import io.gromif.crypto.tink.core.encoders.HexEncoder
import io.gromif.crypto.tink.core.hash.Sha256Util
import io.gromif.crypto.tink.core.hash.Sha384Util
import io.gromif.crypto.tink.core.parsers.KeysetParserWithAead
import io.gromif.crypto.tink.core.serializers.KeysetSerializerWithAead
import io.gromif.crypto.tink.core.utils.DefaultKeysetIdUtil
import io.gromif.crypto.tink.core.utils.DefaultKeystoreKeysetIdUtil
import io.gromif.crypto.tink.data.AssociatedDataManager
import io.gromif.crypto.tink.data.KeysetManager
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
        prefsKeysetIdUtil: DefaultKeysetIdUtil,
        masterKeysetIdUtil: DefaultKeystoreKeysetIdUtil,
    ) = KeysetFactoryImpl(
        keysetDataStoreManager = keysetDataStoreManager,
        keysetSerializerWithAead = keysetSerializerWithAead,
        keysetParserWithAead = keysetParserWithAead,
        prefsKeysetIdUtil = prefsKeysetIdUtil,
        masterKeysetIdUtil = masterKeysetIdUtil
    )

    @Singleton
    @Provides
    fun provideDefaultKeystoreKeysetIdUtil(
        hexEncoder: HexEncoder,
        sha256Util: Sha256Util
    ) = DefaultKeystoreKeysetIdUtil(encoder = hexEncoder, hashUtil = sha256Util)

    @Singleton
    @Provides
    fun provideDefaultKeysetIdUtil(
        hexEncoder: HexEncoder,
        sha384Util: Sha384Util
    ) = DefaultKeysetIdUtil(encoder = hexEncoder, hashUtil = sha384Util)

}