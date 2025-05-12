package io.gromif.notes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.aead.AeadManager
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import io.gromif.notes.data.util.AeadHandler
import io.gromif.notes.data.util.AeadUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UtilModule {

    @Provides
    fun provideAeadUtil(
        aeadManager: AeadManager,
        associatedDataManager: AssociatedDataManager,
        base64Encoder: Base64Encoder,
    ): AeadUtil = AeadUtil(
        aeadManager = aeadManager,
        associatedDataManager = associatedDataManager,
        base64Encoder = base64Encoder,
    )

    @Singleton
    @Provides
    fun provideAeadHandler(aeadUtil: AeadUtil): AeadHandler = AeadHandler(aeadUtil)

}