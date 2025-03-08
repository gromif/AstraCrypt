package io.gromif.astracrypt.di.crypto.tink

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.crypto.tink.core.hash.Sha256Util
import io.gromif.crypto.tink.core.hash.Sha384Util
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HashModule {

    @Singleton
    @Provides
    fun provideSha256Service() = Sha256Util()

    @Singleton
    @Provides
    fun provideSha384Service() = Sha384Util()
}