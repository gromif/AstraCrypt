package io.gromif.astracrypt.di.crypto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.di.datastore.KeysetDataStore
import io.gromif.astracrypt.utils.crypto.DatastoreKeysetReader
import io.gromif.astracrypt.utils.crypto.DatastoreKeysetWriter
import io.gromif.crypto.tink.aead.AeadManager
import io.gromif.crypto.tink.core.encoders.HexEncoder
import io.gromif.crypto.tink.core.hash.Sha256Util
import io.gromif.crypto.tink.core.hash.Sha384Util
import io.gromif.crypto.tink.core.utils.DefaultKeysetIdUtil
import io.gromif.crypto.tink.core.utils.DefaultKeystoreKeysetIdUtil
import io.gromif.crypto.tink.keyset.KeysetManager
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import io.gromif.crypto.tink.keyset.associated_data.GetGlobalAssociatedDataPrf
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithAead
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializerWithAead
import io.gromif.crypto.tink.kms.AndroidKeyManagementService
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeysetManagerModule {

    @Singleton
    @Provides
    fun provideAeadManager(keysetManager: KeysetManager) =
        AeadManager(keysetManager = keysetManager)

    @Singleton
    @Provides
    fun provideKeysetManager(
        defaultKeysetFactory: AndroidKeyManagementService,
        associatedDataManager: AssociatedDataManager,
    ) = KeysetManager(
        keyManagementService = defaultKeysetFactory,
        associatedDataManager = associatedDataManager
    )

    @Singleton
    @Provides
    fun provideAssociatedDataManager(
        @ApplicationContext context: Context,
    ): AssociatedDataManager = AssociatedDataManager(
        associatedDataFile = File("${context.filesDir}/grapefruit.ss0")
    )

    @Singleton
    @Provides
    fun provideGetGlobalAssociatedDataPrf(
        keysetManager: KeysetManager,
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
    ) = AndroidKeyManagementService(
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
        @KeysetDataStore dataStore: DataStore<Preferences>,
    ) = DatastoreKeysetReader(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideDatastoreKeysetWriter(
        @KeysetDataStore dataStore: DataStore<Preferences>,
    ) = DatastoreKeysetWriter(dataStore = dataStore)

    @Singleton
    @Provides
    fun provideDefaultKeystoreKeysetIdUtil(
        hexEncoder: HexEncoder,
        sha256Util: Sha256Util,
    ) = DefaultKeystoreKeysetIdUtil(encoder = hexEncoder, hashUtil = sha256Util)

    @Singleton
    @Provides
    fun provideDefaultKeysetIdUtil(
        hexEncoder: HexEncoder,
        sha384Util: Sha384Util,
    ) = DefaultKeysetIdUtil(encoder = hexEncoder, hashUtil = sha384Util)

}