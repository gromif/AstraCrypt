package com.nevidimka655.astracrypt.app.di.crypto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nevidimka655.astracrypt.app.di.datastore.KeysetDataStore
import com.nevidimka655.astracrypt.data.crypto.DatastoreKeysetReader
import com.nevidimka655.astracrypt.data.crypto.DatastoreKeysetWriter
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
import io.gromif.crypto.tink.data.DefaultKeysetFactory
import io.gromif.crypto.tink.data.KeysetManager
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeysetManagerModule {

    @Singleton
    @Provides
    fun provideKeysetManager(
        defaultKeysetFactory: DefaultKeysetFactory,
        associatedDataManager: AssociatedDataManager
    ) = KeysetManager(
        keysetFactory = defaultKeysetFactory,
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
        datastoreKeysetReader: DatastoreKeysetReader,
        datastoreKeysetWriter: DatastoreKeysetWriter,
        keysetSerializerWithAead: KeysetSerializerWithAead,
        keysetParserWithAead: KeysetParserWithAead,
        prefsKeysetIdUtil: DefaultKeysetIdUtil,
        masterKeysetIdUtil: DefaultKeystoreKeysetIdUtil,
    ) = DefaultKeysetFactory(
        keysetReader = datastoreKeysetReader,
        keysetWriter = datastoreKeysetWriter,
        keysetSerializerWithAead = keysetSerializerWithAead,
        keysetParserWithAead = keysetParserWithAead,
        prefsKeysetIdUtil = prefsKeysetIdUtil,
        masterKeysetIdUtil = masterKeysetIdUtil
    )

    @Singleton
    @Provides
    fun provideDatastoreKeysetReader(
        @KeysetDataStore dataStore: DataStore<Preferences>
    ) = DatastoreKeysetReader(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideDatastoreKeysetWriter(
        @KeysetDataStore dataStore: DataStore<Preferences>
    ) = DatastoreKeysetWriter(dataStore = dataStore)

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