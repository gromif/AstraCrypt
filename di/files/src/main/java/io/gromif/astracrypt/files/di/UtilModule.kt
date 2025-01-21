package io.gromif.astracrypt.files.di

import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.util.AeadUtilImpl
import io.gromif.astracrypt.files.domain.util.AeadUtil
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object UtilModule {

    @Singleton
    @Provides
    fun provideAeadUtil(
        keysetManager: KeysetManager,
        base64Util: Base64Util
    ): AeadUtil = AeadUtilImpl(
        keysetManager = keysetManager,
        base64Util = base64Util
    )

}