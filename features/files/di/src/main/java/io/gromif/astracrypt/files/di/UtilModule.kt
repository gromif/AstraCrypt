package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.util.AeadUtilImpl
import io.gromif.astracrypt.files.domain.util.AeadUtil
import io.gromif.crypto.tink.data.AssociatedDataManager
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.encoders.Base64Util
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UtilModule {

    @Singleton
    @Provides
    fun provideAeadUtil(
        keysetManager: KeysetManager,
        associatedDataManager: AssociatedDataManager,
        base64Util: Base64Util
    ): AeadUtil = AeadUtilImpl(
        keysetManager = keysetManager,
        associatedDataManager = associatedDataManager,
        base64Util = base64Util
    )

}